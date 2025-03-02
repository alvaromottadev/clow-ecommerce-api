package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Carrinho;
import jakarta.transaction.Transactional;
import org.hibernate.query.ResultListTransformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {

    Carrinho findByUsuarioLogin(String login);

    Carrinho findByUsuarioId(String usuarioId);

    List<Carrinho> findAllByItensCarrinhoProdutoId(String produtoId);

}
