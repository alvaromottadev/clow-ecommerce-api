package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.ItemCarrinho;

import java.util.List;

public record CarrinhoResponseDTO(String id, Double total, List<ItemCarrinhoResponseDTO> itens) {

    public CarrinhoResponseDTO(Carrinho carrinho){

        this(carrinho.getId(), carrinho.getTotal(), carrinho.getItensCarrinho().stream().map(ItemCarrinhoResponseDTO::new).toList());

    }
}
