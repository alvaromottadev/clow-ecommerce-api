package br.com.motta.ecommerce.dto.pedido;

import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDTO

        (
                @NotNull(message = "O parâmetro logradouro não pode está nulo.")
                String logradouro,

                @NotNull(message = "O parâmetro numero não pode está nulo.")
                Integer numero,

                @NotNull(message = "O parâmetro bairro não pode está nulo.")
                String bairro,

                String complemento,

                @NotNull(message = "O parâmetro cidade não pode está nulo.")
                String cidade,

                @NotNull(message = "O parâmetro estado não pode está nulo.")
                String estado,

                @NotNull(message = "O parâmetro cep não pode está nulo.")
                String cep) {

}
