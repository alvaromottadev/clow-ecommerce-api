package br.com.motta.ecommerce.dto.estoque;

import br.com.motta.ecommerce.model.Estoque;

public record EstoqueAtualizadoResponseDTO (String nome, String tamanho, Integer quantidade){

    public EstoqueAtualizadoResponseDTO(Estoque estoque){

        this(estoque.getProdutoEstoque().getNome(), estoque.getTamanho(), estoque.getQuantidade());
    }

}
