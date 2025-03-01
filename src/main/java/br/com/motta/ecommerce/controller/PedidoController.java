package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping("/efetuar-pedido/{userId}")
    public void efetuarPedido(@PathVariable String userId){
        service.efetuarPedido(userId);
    }

}
