package br.com.motta.ecommerce.dto.pedido;

import br.com.motta.ecommerce.model.ItemPedido;

public record ItemPedidoResponseDTO(String nome, String tamanho, Integer quantidade, Double preco) {

    public ItemPedidoResponseDTO(ItemPedido pedido){

        this(pedido.getProdutoNome(), pedido.getTamanho(), pedido.getQuantidade(), pedido.getPreco());

    }

}
