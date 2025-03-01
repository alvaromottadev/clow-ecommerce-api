package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Estoque findByTamanhoAndProdutoEstoqueId(String tamanho, String produtoEstoqueId);

}
