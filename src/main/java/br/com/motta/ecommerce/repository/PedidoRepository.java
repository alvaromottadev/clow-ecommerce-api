package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByClientePedidoLogin(String clientePedidoLogin);

    Optional<Pedido> findByIdAndClientePedidoLogin(Long id, String clientePedidoLogin);

}
