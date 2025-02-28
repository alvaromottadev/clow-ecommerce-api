package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.UsuarioAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.UsuarioRequestDTO;
import br.com.motta.ecommerce.dto.UsuarioResponseDTO;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios(){
        return service.getAllUsuarios();
    }

    @GetMapping("/get/{login}")
    public ResponseEntity<UsuarioResponseDTO> getUsuario(@PathVariable String login){
        return service.getUsuario(login);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registerUsuario(@RequestBody UsuarioRequestDTO usuario){
        Usuario usuarioCriado = new Usuario(usuario.username(), usuario.login(), usuario.password(), usuario.saldo());
        return service.registerUsuario(usuarioCriado);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ResultDTO> atualizarUsuario(@Validated @RequestBody UsuarioAtualizarRequestDTO data){
        return service.atualizarUsuario(data);
    }

    @DeleteMapping("/deletar/{login}")
    public ResponseEntity<ResultDTO> deletarUsuario(@PathVariable String login){
        return service.deletarUsuario(login);
    }

}
