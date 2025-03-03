package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

    Optional<ItemCarrinho> findByProdutoId(String produtoId);

    Optional<ItemCarrinho> findByTamanhoAndProdutoIdAndCarrinhoId(String tamanho, String produtoId, String carrinhoId);

    List<ItemCarrinho> findAllByCarrinhoId(String carrinhoId);

}
