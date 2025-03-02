package br.com.motta.ecommerce.infra.security;

import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return new UsuarioDetails(usuario);
    }
}
