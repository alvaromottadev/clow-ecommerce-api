package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    Optional<Produto> findByApelido(String apelido);
}
