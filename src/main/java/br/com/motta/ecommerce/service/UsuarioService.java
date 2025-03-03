package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.usuario.UsuarioAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.com.motta.ecommerce.exception.DuplicateLoginException;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.infra.security.JwtTokenUtil;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios(){
        return ResponseEntity.ok(repository.findAll().stream().map(UsuarioResponseDTO::new).toList());
    }

    public ResponseEntity<UsuarioResponseDTO> getPerfil(String token){
        String login = JwtTokenUtil.getLogin(token);
        Usuario usuario = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    public ResponseEntity<UsuarioResponseDTO> getUsuario(String login){
        Usuario usuario = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    @Transactional
    public ResponseEntity<UsuarioResponseDTO> registerUsuario(Usuario usuario){
        if (repository.existsByLogin(usuario.getLogin())){
            throw new DuplicateLoginException("Já existe um usuário com esse email cadastrado.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinho.setTotal(0.0);
        carrinhoRepository.save(carrinho);
        return ResponseEntity.status(201).body(new UsuarioResponseDTO(usuario));
    }

    @Transactional
    public ResponseEntity<ResultDTO> atualizarPerfil(String token, UsuarioAtualizarRequestDTO data){
        String login = JwtTokenUtil.getLogin(token);
        Usuario usuario = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        updateUsuario(usuario, data);
        return ResponseEntity.ok(new ResultDTO("Perfil atualizado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> atualizarUsuario(String login, UsuarioAtualizarRequestDTO data){
        Usuario usuario = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        updateUsuario(usuario, data);
        return ResponseEntity.ok(new ResultDTO("Usuário atualizado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> deletarPerfil(String token){
        String login = JwtTokenUtil.getLogin(token);
        Usuario usuario = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        repository.delete(usuario);
        return ResponseEntity.ok(new ResultDTO("Perfil deletado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> deletarUsuario(String login){
        Usuario usuario = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        repository.delete(usuario);
        return ResponseEntity.ok(new ResultDTO("Usuário deletado com sucesso."));
    }

    private void updateUsuario(Usuario usuario, UsuarioAtualizarRequestDTO data){
        usuario.setUsername(data.username());
        usuario.setLogin(data.login());
        usuario.setPassword(passwordEncoder.encode(data.password()));
        repository.save(usuario);
    }

}
