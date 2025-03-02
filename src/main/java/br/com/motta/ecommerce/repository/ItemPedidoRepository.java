package br.com.motta.ecommerce.repository;

import br.com.motta.ecommerce.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
