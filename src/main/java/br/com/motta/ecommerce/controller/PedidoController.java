package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.pedido.EnderecoRequestDTO;
import br.com.motta.ecommerce.dto.pedido.PedidoResponseDTO;
import br.com.motta.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping("/get/{pedidoId}")
    public ResponseEntity<PedidoResponseDTO> findPedido(@RequestHeader("Authorization") String token, @PathVariable Long pedidoId){
        return service.findPedido(token, pedidoId);
    }

    @GetMapping("/get-all/{login}")
    public ResponseEntity<List<PedidoResponseDTO>> findAllPedidos(@PathVariable String login){
        return service.findAllPedidosByClienteLogin(login);
    }

    @PostMapping("/efetuar-pedido")
    public ResponseEntity<?> efetuarPedido(@RequestHeader("Authorization") String token, @Validated @RequestBody EnderecoRequestDTO endereco){
        return service.efetuarPedido(token, endereco);
    }



}
