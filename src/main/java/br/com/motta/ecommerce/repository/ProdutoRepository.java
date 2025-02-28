package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    Produto findByApelido(String apelido);
}
