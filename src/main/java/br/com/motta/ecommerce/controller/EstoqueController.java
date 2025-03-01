package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.EstoqueAtualizadoResponseDTO;
import br.com.motta.ecommerce.dto.EstoqueResponseDTO;
import br.com.motta.ecommerce.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EstoqueAtualizadoResponseDTO> adicionarEstoque(@PathVariable String id,
                                                                         @PathVariable String tamanho,
                                                                         @PathVariable Integer quantidade){
        return service.adicionarEstoque(id, tamanho, quantidade);
    }

    @PostMapping("/remover/{id}/{tamanho}/{quantidade}")
    public ResponseEntity<EstoqueAtualizadoResponseDTO> removerEstoque(@PathVariable String id,
                               @PathVariable String tamanho,
                               @PathVariable Integer quantidade){
        return service.removerEstoque(id, tamanho, quantidade);
    }

}
