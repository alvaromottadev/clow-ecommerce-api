package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.dto.carrinho.CarrinhoResponseDTO;
import br.com.motta.ecommerce.exception.NoStockException;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.infra.security.JwtTokenUtil;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.Estoque;
import br.com.motta.ecommerce.model.ItemCarrinho;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.EstoqueRepository;
import br.com.motta.ecommerce.repository.ItemCarrinhoRepository;
import br.com.motta.ecommerce.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public ResponseEntity<CarrinhoResponseDTO> getCarrinho(String token) {
        String login = JwtTokenUtil.getLogin(token);
        Carrinho carrinho = repository.findByClienteLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        return ResponseEntity.ok(new CarrinhoResponseDTO(carrinho));
    }

    public ResponseEntity<CarrinhoResponseDTO> getCarrinhoByLogin(String login){
        Carrinho carrinho = repository.findByClienteLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        return ResponseEntity.ok(new CarrinhoResponseDTO(carrinho));
    }

    @Transactional
    public ResponseEntity<ResponseDTO> addProduto(String token, String apelido, String tamanho) {
        String login = JwtTokenUtil.getLogin(token);
        tamanho = tamanho.toUpperCase();
        Carrinho carrinho = repository.findByClienteLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        Produto produto = produtoRepository.findByApelido(apelido).orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        Estoque estoque = estoqueRepository.findByTamanhoAndProdutoEstoqueId(tamanho, produto.getId()).orElse(null);
        if (estoque == null){
            throw new NotFoundException("Tamanho não encontrado.");
        }
        if (estoque.getQuantidade() <= 0){
            throw new NoStockException("O produto " + produto.getNome() + " - " + tamanho + " não possui estoque.");
        }

        adicionarProduto(carrinho, produto, tamanho);

        repository.save(carrinho);
        return ResponseEntity.ok(new ResponseDTO("Produto adicionado no carrinho com sucesso."));

    }

    @Transactional
    public ResponseEntity<ResponseDTO> deletarProdutoCarrinho(String token, String apelido, String tamanho) {
        String login = JwtTokenUtil.getLogin(token);
        Carrinho carrinho = repository.findByClienteLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        Produto produto = produtoRepository.findByApelido(apelido).orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        if (deletarProduto(carrinho, produto, tamanho)){
            Double preco = produto.getPreco();
            Double desconto = produto.getDesconto();
            Double total = carrinho.getTotal();
            carrinho.setTotal(total - (preco * (1 - desconto)));
            repository.save(carrinho);
            return ResponseEntity.ok(new ResponseDTO("Produto retirado do carrinho."));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("Produto não encontrado no carrinho."));
    }

    @Transactional
    public ResponseEntity<ResponseDTO> removerProduto(String token, String apelido, String tamanho){
        String login = JwtTokenUtil.getLogin(token);
        Carrinho carrinho = repository.findByClienteLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        Produto produto = produtoRepository.findByApelido(apelido).orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        if (removerProduto(carrinho, produto, tamanho)) {
            Double preco = produto.getPreco();
            Double desconto = produto.getDesconto();
            Double total = carrinho.getTotal();
            carrinho.setTotal(total - (preco * (1 - desconto)));
            repository.save(carrinho);
            return ResponseEntity.ok(new ResponseDTO("Produto removido com sucesso."));
        }
        return ResponseEntity.badRequest().body(new ResponseDTO("Produto não encontrado no carrinho."));

    }

    private void adicionarProduto(Carrinho carrinho, Produto produto, String tamanho) {
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByTamanhoAndProdutoIdAndCarrinhoId(tamanho, produto.getId(), carrinho.getId()).orElse(null);
        if (itemCarrinho != null) {
            if (itemCarrinho.getTamanho().equals(tamanho)) {
                carrinho.addTotal(itemCarrinho.getProduto().getPreco(), itemCarrinho.getProduto().getDesconto());
                itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
                return;
            }
        }
        ItemCarrinho itemCarrinhoCriado = new ItemCarrinho(carrinho, produto, 1, tamanho);
        carrinho.addItem(itemCarrinhoCriado);
    }

    private boolean deletarProduto(Carrinho carrinho, Produto produto, String tamanho) {
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByTamanhoAndProdutoIdAndCarrinhoId(tamanho, produto.getId(), carrinho.getId()).orElse(null);
        if (itemCarrinho != null){
            carrinho.getItensCarrinho().remove(itemCarrinho);
            carrinho.removeTotal(itemCarrinho.getQuantidade(), itemCarrinho.getProduto().getPreco(), itemCarrinho.getProduto().getDesconto());
            itemCarrinhoRepository.delete(itemCarrinho);
            return true;
        }
        return false;
    }

    private boolean removerProduto(Carrinho carrinho, Produto produto, String tamanho){
        Optional<ItemCarrinho> item = carrinho.getItensCarrinho().stream().filter(i -> i.getProduto().equals(produto)).findFirst();
        if (item.isPresent()){
            ItemCarrinho itemCarrinho = item.get();
            if (itemCarrinho.getQuantidade() > 1) {
                itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
                return true;
            }
            deletarProduto(carrinho, produto, tamanho);
            return true;
        }
        return false;
    }

}
