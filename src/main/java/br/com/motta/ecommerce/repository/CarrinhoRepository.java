package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {

    Carrinho findByUsuarioLogin(String login);

}
