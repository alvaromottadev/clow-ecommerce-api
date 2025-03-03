package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {

    Optional<Carrinho> findByClienteLogin(String login);

    Optional<Carrinho> findByClienteId(String usuarioId);

    List<Carrinho> findAllByItensCarrinhoProdutoId(String produtoId);

}
