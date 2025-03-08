package br.com.motta.ecommerce.infra.security;

import br.com.motta.ecommerce.model.Cliente;
import org.springframework.security.config.annotation.web.headers.HeadersSecurityMarker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ClienteDetails implements UserDetails {

    private final Cliente cliente;

    public ClienteDetails(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getId(){
        return cliente.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + cliente.getRole().toString().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return cliente.getPassword();
    }

    @Override
    public String getUsername() {
        return cliente.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
