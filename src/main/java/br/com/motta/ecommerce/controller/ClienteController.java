package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.cliente.ClienteAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.cliente.ClienteRequestDTO;
import br.com.motta.ecommerce.dto.cliente.ClienteResponseDTO;
import br.com.motta.ecommerce.model.Cliente;
import br.com.motta.ecommerce.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<ClienteResponseDTO>> getAllCliente(){
        return service.getAllClientes();
    }

    //GET para visualizar o próprio usuário (perfil)
    @GetMapping("/get")
    public ResponseEntity<ClienteResponseDTO> getPerfil(@RequestHeader("Authorization") String token){
        return service.getPerfil(token);
    }

    //GET para visualizar
    @GetMapping("/get/{login}")
    public ResponseEntity<?> getCliente(@PathVariable String login){
        return service.getCliente(login);
    }

    @PostMapping({"/register", "/register/"})
    public ResponseEntity<ResultDTO> registerCliente(@Validated @RequestBody ClienteRequestDTO cliente){
        Cliente clienteCriado = new Cliente(cliente.username(), cliente.login(), cliente.password(), cliente.role());
        return service.registerCliente(clienteCriado);
    }

    //PUT para atualizar o próprio usuário (perfil)
    @PutMapping("/atualizar")
    public ResponseEntity<ResultDTO> atualizarPerfil(@RequestHeader("Authorization") String token, @Validated @RequestBody ClienteAtualizarRequestDTO data){
        return service.atualizarPerfil(token, data);
    }

    //PUT para atualizar qualquer usuário pelo login
    @PutMapping("/atualizar/{login}")
    public ResponseEntity<ResultDTO> atualizarCliente(@PathVariable String login, @Validated @RequestBody ClienteAtualizarRequestDTO data){
        return service.atualizarCliente(login,data);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<ResultDTO> deletarPerfil(@RequestHeader("Authorization") String token){
        System.out.println(token);
        return service.deletarPerfil(token);
    }

    @DeleteMapping("/deletar/{login}")
    public ResponseEntity<ResultDTO> deletarCliente(@PathVariable String login){
        return service.deletarCliente(login);
    }

}
