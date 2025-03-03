package br.com.motta.ecommerce.dto.cliente;

import br.com.motta.ecommerce.model.Cliente;

public record ClienteResponseDTO(String id, String username, String login, String role) {

    public ClienteResponseDTO(Cliente cliente) {

        this(cliente.getId(), cliente.getUsername(), cliente.getLogin(), cliente.getRole().getRole());

    }

}
