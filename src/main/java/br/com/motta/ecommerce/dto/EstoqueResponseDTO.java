package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.model.Estoque;

public record EstoqueResponseDTO(String tamanho, Integer quantidade) {

    public EstoqueResponseDTO(Estoque estoque){
        this(estoque.getTamanho(), estoque.getQuantidade());
    }

}
