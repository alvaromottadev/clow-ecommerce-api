package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.model.Usuario;

public record UsuarioResponseDTO(String id, String username, String login, Double saldo) {

    public UsuarioResponseDTO(Usuario usuario) {

        this(usuario.getId(), usuario.getUsername(), usuario.getLogin(), usuario.getSaldo());

    }

}
