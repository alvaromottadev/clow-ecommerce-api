package br.com.motta.ecommerce.dto.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioAtualizarRequestDTO(

        @NotNull(message = "O parâmetro id não pode está nulo.")
        @Size(max = 50, message = "O username pode ter no máximo 50 caracteres.")
        String username,

        @NotNull(message = "O parâmetro login não pode está nulo.")
        String login,

        @NotNull(message = "O parâmetro password não pode está nulo.")
        String password
        ) {
}
