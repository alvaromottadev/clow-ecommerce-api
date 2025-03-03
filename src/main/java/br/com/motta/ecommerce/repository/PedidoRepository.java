package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByUsuarioPedidoId(String usuarioPedidoId);

    Optional<Pedido> findByIdAndUsuarioPedidoLogin(Long id, String usuarioPedidoLogin);

}
