package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.UsuarioAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.UsuarioResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

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
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinho.setTotal(0.0);
        carrinhoRepository.save(carrinho);
        return ResponseEntity.status(201).body(new UsuarioResponseDTO(usuario));
    }

    public ResponseEntity<ResultDTO> atualizarUsuario(UsuarioAtualizarRequestDTO data){
        Usuario usuario = repository.findById(data.id()).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        updateUsuario(usuario, data);
        return ResponseEntity.ok(new ResultDTO("Usuário atualizado com sucesso."));
    }

    public ResponseEntity<ResultDTO> deletarUsuario(String login){
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null){
            throw new NotFoundException("O usuário não foi encontrado.");
        }
        repository.delete(usuario);
        return ResponseEntity.ok(new ResultDTO("O usuário foi removido com sucesso."));
    }

    private void updateUsuario(Usuario usuario, UsuarioAtualizarRequestDTO data){
        usuario.setUsername(data.username());
        usuario.setLogin(data.login());
        usuario.setPassword(data.password());

        repository.save(usuario);
    }

}
