package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByLogin(String login);

    boolean existsByLogin(String login);

}
