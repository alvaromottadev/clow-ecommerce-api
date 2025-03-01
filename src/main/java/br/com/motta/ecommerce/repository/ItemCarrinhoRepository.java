package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

    ItemCarrinho findByProdutoId(String produtoId);

    List<ItemCarrinho> findAllByProdutoId(String produtoId);

}
