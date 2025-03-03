package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByTamanhoAndProdutoEstoqueApelido(String tamanho, String produtoEstoqueApelido);

    Optional<Estoque> findByTamanhoAndProdutoEstoqueId(String tamanho, String id);

}
