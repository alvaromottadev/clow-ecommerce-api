package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.model.Estoque;
import br.com.motta.ecommerce.model.ItemEstoque;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProdutoRequestDTO(

        @NotNull(message = "O parâmetro nome não pode está null.")
        String nome,

        @NotNull(message = "O parâmetro descrição não pode está null.")
        String descricao,

        @NotNull(message = "O parâmetro categoria não pode está null.")
        String categoria,

        @NotNull
        List<EstoqueRequestDTO> estoques,

        List<String> imagemUrl,

        @NotNull(message = "O parâmetro preço não pode está null.")
        Double preco,

        Double desconto,

        Integer quantidade)

{

}
