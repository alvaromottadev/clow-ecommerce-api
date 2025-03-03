package br.com.motta.ecommerce.dto.produto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProdutoAtualizarRequestDTO(

        @NotNull(message = "O parâmetro apelido não pode está nulo.")
        String apelido,

        @NotNull(message = "O parâmetro nome não pode está nulo.")
        String nome,

        @NotNull(message = "O parâmetro descricao id não pode está nulo.")
        String descricao,

        @NotNull(message = "O parâmetro categoria não pode está nulo.")
        String categoria,

        List<String> imagemUrl,

        @NotNull(message = "O parâmetro preco não pode está nulo.")
        Double preco,

        @NotNull(message = "O parâmetro desconto não pode está nulo.")
        Double desconto) {
}
