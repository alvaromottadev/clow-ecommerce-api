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

    @PostMapping("/adicionar/{apelido}/{tamanho}")
    public ResponseEntity<CarrinhoResponseDTO> addProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho,
                                                          @PathVariable String apelido,
                                                          @PathVariable String tamanho){
        return service.addProduto(carrinho.login(), apelido, tamanho);
    }

    @DeleteMapping("/deletar/{apelido}/{tamanho}")
    public ResponseEntity<ResultDTO> deletarProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho,
                                                    @PathVariable String apelido,
                                                    @PathVariable String tamanho){
        return service.deletarProdutoCarrinho(carrinho.login(), apelido, tamanho);
    }

    @DeleteMapping("/remover/{apelido}/{tamanho}")
    public ResponseEntity<ResultDTO> removerProduto(@Validated @RequestBody CarrinhoRequestDTO carrinho,
                                                    @PathVariable String apelido,
                                                    @PathVariable String tamanho){
        return service.removerProduto(carrinho.login(), apelido, tamanho);
    }
}
