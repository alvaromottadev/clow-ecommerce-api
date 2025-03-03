package br.com.motta.ecommerce.dto.produto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProdutoAtualizarRequestDTO(

        @NotNull(message = "O apelido id não pode está nulo.")
        String apelido,

        @NotNull(message = "O nome id não pode está nulo.")
        String nome,

        @NotNull(message = "O descricao id não pode está nulo.")
        String descricao,

        List<String> imagemUrl,

        @NotNull(message = "O preco id não pode está nulo.")
        Double preco,

        @NotNull(message = "O desconto id não pode está nulo.")
        Double desconto)

         {
}
