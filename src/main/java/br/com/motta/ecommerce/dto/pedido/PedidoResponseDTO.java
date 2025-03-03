package br.com.motta.ecommerce.dto.pedido;

import br.com.motta.ecommerce.model.Pedido;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO
        (LocalDateTime dataPedido, Double total, String endereco, List<ItemPedidoResponseDTO> itensPedido) {

    public PedidoResponseDTO(Pedido pedido){
        this(pedido.getDataPedido(), pedido.getTotal(), pedido.getEndereco(), pedido.getItensPedido().stream().map(ItemPedidoResponseDTO::new).toList());
    }

}
