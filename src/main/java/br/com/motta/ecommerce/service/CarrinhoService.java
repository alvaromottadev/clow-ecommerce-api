package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.CarrinhoResponseDTO;
import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.ItemCarrinho;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.ItemCarrinhoRepository;
import br.com.motta.ecommerce.repository.ProdutoRepository;
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

    public ResponseEntity<CarrinhoResponseDTO> getCarrinho(String login) {
        Carrinho carrinho = repository.findByUsuarioLogin(login);
        if (carrinho == null) {
            throw new NotFoundException("Carrinho não encontrado.");
        }
        return ResponseEntity.ok(new CarrinhoResponseDTO(carrinho));
    }

    public ResponseEntity<CarrinhoResponseDTO> addProduto(String login, String apelido, String tamanho) {

        Carrinho carrinho = repository.findByUsuarioLogin(login);
        if (carrinho == null) {
            throw new NotFoundException("Carrinho não encontrado.");
        }

        Produto produto = produtoRepository.findByApelido(apelido);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado.");
        }

        if (!produto.getTamanhos().contains(tamanho)){
            throw new NotFoundException("Tamanho não disponível.");
        }

        adicionarProduto(carrinho, produto, tamanho);

        repository.save(carrinho);
        return ResponseEntity.ok(new CarrinhoResponseDTO(carrinho));

    }

    public ResponseEntity<ResultDTO> deletarProdutoCarrinho(String login, String apelido, String tamanho) {
        Carrinho carrinho = repository.findByUsuarioLogin(login);
        if (carrinho == null) {
            throw new NotFoundException("Carrinho não encontrado.");
        }

        Produto produto = produtoRepository.findByApelido(apelido);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado.");
        }

        if (deletarProduto(carrinho, produto, tamanho)){
            Double preco = produto.getPreco();
            Double desconto = produto.getDesconto();
            Double total = carrinho.getTotal();
            carrinho.setTotal(total - (preco * (1 - desconto)));
            repository.save(carrinho);
            return ResponseEntity.ok(new ResultDTO("Produto removido do carrinho."));
        }
        return ResponseEntity.badRequest().body(new ResultDTO("Produto não encontrado no carrinho."));
    }

    public ResponseEntity<ResultDTO> removerProduto(String login, String apelido, String tamanho){

        Carrinho carrinho = repository.findByUsuarioLogin(login);
        if (carrinho == null) {
            throw new NotFoundException("Carrinho não encontrado.");
        }

        Produto produto = produtoRepository.findByApelido(apelido);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado.");
        }

        if (removerProduto(carrinho, produto, tamanho)) {
            Double preco = produto.getPreco();
            Double desconto = produto.getDesconto();
            Double total = carrinho.getTotal();
            carrinho.setTotal(total - (preco * (1 - desconto)));
            repository.save(carrinho);
            return ResponseEntity.ok(new ResultDTO("Produto removido com sucesso."));
        }
        return ResponseEntity.badRequest().body(new ResultDTO("Produto não encontrado no carrinho."));

    }

    private void adicionarProduto(Carrinho carrinho, Produto produto, String tamanho) {
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByTamanhoAndProdutoIdAndCarrinhoId(tamanho, produto.getId(), carrinho.getId());
        if (itemCarrinho != null) {
            if (itemCarrinho.getTamanho().equals(tamanho)) {
                itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
                return;
            }
        }
        ItemCarrinho itemCarrinhoCriado = new ItemCarrinho(carrinho, produto, 1, tamanho);
        carrinho.addItem(itemCarrinhoCriado);
    }

    private boolean deletarProduto(Carrinho carrinho, Produto produto, String tamanho) {
        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByTamanhoAndProdutoIdAndCarrinhoId(tamanho, produto.getId(), carrinho.getId());
        if (itemCarrinho != null){
            carrinho.getItensCarrinho().remove(itemCarrinho);
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
