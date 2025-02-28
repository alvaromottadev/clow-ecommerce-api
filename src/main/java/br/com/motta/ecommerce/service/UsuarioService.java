package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.DeleteResponseDTO;
import br.com.motta.ecommerce.dto.UsuarioResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios(){
        return ResponseEntity.ok(repository.findAll().stream().map(UsuarioResponseDTO::new).toList());
    }

    public ResponseEntity<UsuarioResponseDTO> getUsuario(String login){
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null){
            throw new NotFoundException("O usuário não foi encontrado.");
        }
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    public ResponseEntity<UsuarioResponseDTO> registerUsuario(Usuario usuario){
        if (usuario.getSaldo() == null){
            usuario.setSaldo(0.0);
        }
        repository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    public ResponseEntity<DeleteResponseDTO> deletarUsuario(String login){
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null){
            throw new NotFoundException("O usuário não foi encontrado.");
        }
        repository.delete(usuario);
        return ResponseEntity.ok(new DeleteResponseDTO("O usuário foi removido com sucesso."));
    }

}
