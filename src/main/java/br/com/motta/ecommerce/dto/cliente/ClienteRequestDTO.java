package br.com.motta.ecommerce.dto.cliente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(

        @NotNull
        @Size(max = 50, message = "O username pode ter no máximo 50 caracteres.")
        String username,

        @NotNull
        String login,

        @NotNull
        String password) {

}
