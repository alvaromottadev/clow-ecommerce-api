package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.CarrinhoRequestDTO;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @GetMapping("/{login}")
    public ResponseEntity<?> getCarrinho(@PathVariable String login){
        return service.getCarrinho(login);
    }

    @PostMapping("/adicionar/{apelido}")
    public ResponseEntity<?> addProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho, @PathVariable String apelido){
        return service.addProduto(carrinho.login(), apelido);
    }

}
