package br.com.motta.ecommerce.dto.carrinho;

import br.com.motta.ecommerce.model.Carrinho;

import java.util.List;

public record CarrinhoResponseDTO(String total, List<ItemCarrinhoResponseDTO> itens) {

    public CarrinhoResponseDTO(Carrinho carrinho){

        this(String.format("%.2f",carrinho.getTotal()), carrinho.getItensCarrinho().stream().map(ItemCarrinhoResponseDTO::new).toList());

    }
}
