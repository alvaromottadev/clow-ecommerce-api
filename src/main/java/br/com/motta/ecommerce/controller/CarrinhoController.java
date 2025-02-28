package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.CarrinhoRequestDTO;
import br.com.motta.ecommerce.dto.CarrinhoResponseDTO;
import br.com.motta.ecommerce.dto.ResultDTO;
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
    public ResponseEntity<CarrinhoResponseDTO> getCarrinho(@PathVariable String login){
        return service.getCarrinho(login);
    }

    @PostMapping("/adicionar/{apelido}")
    public ResponseEntity<CarrinhoResponseDTO> addProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho, @PathVariable String apelido){
        return service.addProduto(carrinho.login(), apelido);
    }

    @DeleteMapping("/deletar/{apelido}")
    public ResponseEntity<ResultDTO> deletarProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho, @PathVariable String apelido){
        return service.deletarProdutoCarrinho(carrinho.login(), apelido);
    }

    @DeleteMapping("/remover/{apelido}")
    public ResponseEntity<ResultDTO> removerProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho, @PathVariable String apelido){
        return service.removerProduto(carrinho.login(), apelido);
    }
}
