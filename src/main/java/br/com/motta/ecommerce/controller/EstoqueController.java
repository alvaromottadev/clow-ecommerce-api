package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.estoque.EstoqueAtualizadoResponseDTO;
import br.com.motta.ecommerce.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @GetMapping("/get/{apelido}")
    public ResponseEntity<?> getEstoque(@PathVariable String apelido){
        return null;
    }

    @PostMapping("/adicionar/{apelido}/{tamanho}/{quantidade}")
    public ResponseEntity<EstoqueAtualizadoResponseDTO> adicionarEstoque(@PathVariable String apelido,
                                                                         @PathVariable String tamanho,
                                                                         @PathVariable Integer quantidade){
        return service.adicionarEstoque(apelido, tamanho, quantidade);
    }

    @PostMapping("/remover/{apelido}/{tamanho}/{quantidade}")
    public ResponseEntity<EstoqueAtualizadoResponseDTO> removerEstoque(@PathVariable String apelido,
                               @PathVariable String tamanho,
                               @PathVariable Integer quantidade){
        return service.removerEstoque(apelido, tamanho, quantidade);
    }

}
