package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.usuario.UsuarioAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.usuario.UsuarioRequestDTO;
import br.com.motta.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios(){
        return service.getAllUsuarios();
    }

    //GET para visualizar o próprio usuário (perfil)
    @GetMapping("/get")
    public ResponseEntity<UsuarioResponseDTO> getPerfil(@RequestHeader("Authorization") String token){
        return service.getPerfil(token);
    }

    //GET para visualizar
    @GetMapping("/get/{login}")
    public ResponseEntity<?> getUsuario(@PathVariable String login){
        return service.getUsuario(login);
    }

    @PostMapping({"/register", "/register/"})
    public ResponseEntity<UsuarioResponseDTO> registerUsuario(@Validated @RequestBody UsuarioRequestDTO usuario){
        Usuario usuarioCriado = new Usuario(usuario.username(), usuario.login(), usuario.password(), usuario.role());
        return service.registerUsuario(usuarioCriado);
    }

    //PUT para atualizar o próprio usuário (perfil)
    @PutMapping("/atualizar")
    public ResponseEntity<ResultDTO> atualizarPerfil(@RequestHeader("Authorization") String token, @Validated @RequestBody UsuarioAtualizarRequestDTO data){
        return service.atualizarPerfil(token, data);
    }

    //PUT para atualizar qualquer usuário pelo login
    @PutMapping("/atualizar/{login}")
    public ResponseEntity<ResultDTO> atualizarUsuario(@PathVariable String login, @Validated @RequestBody UsuarioAtualizarRequestDTO data){
        return service.atualizarUsuario(login,data);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<ResultDTO> deletarPerfil(@RequestHeader("Authorization") String token){
        System.out.println(token);
        return service.deletarPerfil(token);
    }

    @DeleteMapping("/deletar/{login}")
    public ResponseEntity<ResultDTO> deletarUsuario(@PathVariable String login){
        return service.deletarUsuario(login);
    }

}
