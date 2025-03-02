package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByUsuarioPedidoId(String usuarioPedidoId);

}
