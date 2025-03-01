package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

    ItemCarrinho findByProdutoId(String produtoId);

    ItemCarrinho findByTamanhoAndProdutoIdAndCarrinhoId(String tamanho, String produtoId, String carrinhoId);

}
