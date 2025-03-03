package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.carrinho.CarrinhoRequestDTO;
import br.com.motta.ecommerce.dto.carrinho.CarrinhoResponseDTO;
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

    @GetMapping("/get")
    public ResponseEntity<CarrinhoResponseDTO> getCarrinho(@RequestHeader("Authorization") String token) {
        return service.getCarrinho(token);
    }

    @GetMapping("/get/{login}")
    public ResponseEntity<?> getCarrinhoByLogin(@PathVariable String login) {
        return service.getCarrinhoByLogin(login);
    }

    @PostMapping("/adicionar/{apelido}/{tamanho}")
    public ResponseEntity<ResultDTO> addProduto(@RequestHeader("Authorization") String token,
                                                          @PathVariable String apelido,
                                                          @PathVariable String tamanho) {
        return service.addProduto(token, apelido, tamanho);
    }

    @DeleteMapping("/deletar/{apelido}/{tamanho}")
    public ResponseEntity<ResultDTO> deletarProduto(@RequestHeader("Authorization") String token,
                                                    @PathVariable String apelido,
                                                    @PathVariable String tamanho) {
        return service.deletarProdutoCarrinho(token, apelido, tamanho);
    }

    @DeleteMapping("/remover/{apelido}/{tamanho}")
    public ResponseEntity<ResultDTO> removerProduto(@RequestHeader("Authorization") String token,
                                                    @PathVariable String apelido,
                                                    @PathVariable String tamanho) {
        return service.removerProduto(token, apelido, tamanho);
    }
}
