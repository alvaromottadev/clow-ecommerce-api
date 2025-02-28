package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.CarrinhoResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.ItemCarrinho;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<?> getCarrinho(String login){
        Carrinho carrinho = repository.findByUsuarioLogin(login);
        if (carrinho == null){
            throw new NotFoundException("Carrinho não encontrado.");
        }
        return ResponseEntity.ok(new CarrinhoResponseDTO(carrinho));
    }

    public ResponseEntity<?> addProduto(String login, String apelido){

        Carrinho carrinho = repository.findByUsuarioLogin(login);
        if (carrinho == null){
            throw new NotFoundException("Carrinho não encontrado.");
        }

        Produto produto = produtoRepository.findByApelido(apelido);
        if (produto == null){
            throw new NotFoundException("Produto não encontrado.");
        }

        validateProduto(carrinho, produto);

        Double preco = produto.getPreco();
        Double desconto = produto.getDesconto();
        Double total = carrinho.getTotal();
        carrinho.setTotal(total + (preco * (1 - desconto)));

        repository.save(carrinho);
        return ResponseEntity.ok(new CarrinhoResponseDTO(carrinho));

    }

    private void validateProduto(Carrinho carrinho, Produto produto){
        Optional<ItemCarrinho> item = carrinho.getItensCarrinho().stream().filter(i -> i.getProduto().equals(produto)).findFirst();
        if (item.isPresent()){
            item.get().setQuantidade(item.get().getQuantidade() + 1);
            return;
        }
        ItemCarrinho itemCarrinho = new ItemCarrinho(carrinho, produto, 1);
        carrinho.addItem(itemCarrinho);
    }

}
