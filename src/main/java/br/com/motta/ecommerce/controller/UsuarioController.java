package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.DeleteResponseDTO;
import br.com.motta.ecommerce.dto.UsuarioRequestDTO;
import br.com.motta.ecommerce.dto.UsuarioResponseDTO;
import br.com.motta.ecommerce.model.Usuario;
import br.com.motta.ecommerce.service.UsuarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/deletar/{login}")
    public ResponseEntity<DeleteResponseDTO> deletarUsuario(@PathVariable String login){
        return service.deletarUsuario(login);
    }

}
