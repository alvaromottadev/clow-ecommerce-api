package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EnderecoRequestDTO;
import br.com.motta.ecommerce.dto.PedidoResponseDTO;
import br.com.motta.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping("/ver/{pedidoId}")
    public ResponseEntity<PedidoResponseDTO> findPedido(@PathVariable Long pedidoId){
        return service.findPedido(pedidoId);
    }

    @GetMapping("/ver-todos/{usuarioId}")
    public ResponseEntity<List<PedidoResponseDTO>> findAllPedidos(@PathVariable String usuarioId){
        return service.findAllPedidosByUsuarioId(usuarioId);
    }

    @PostMapping("/efetuar-pedido/{usuarioId}")
    public void efetuarPedido(@PathVariable String usuarioId, @RequestBody EnderecoRequestDTO endereco){
        service.efetuarPedido(usuarioId, endereco);
    }



}
