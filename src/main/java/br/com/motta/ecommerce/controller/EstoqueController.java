package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @PostMapping("/adicionar/{id}/{tamanho}/{quantidade}")
    public void adicionarEstoque(@PathVariable String id,
                                 @PathVariable String tamanho,
                                 @PathVariable Integer quantidade){
        service.adicionarEstoque(id, tamanho, quantidade);
    }

    @PostMapping("/remover/{id}/{tamanho}/{quantidade}")
    public void removerEstoque(@PathVariable String id,
                               @PathVariable String tamanho,
                               @PathVariable Integer quantidade){
        service.removerEstoque(id, tamanho, quantidade);
    }

}
