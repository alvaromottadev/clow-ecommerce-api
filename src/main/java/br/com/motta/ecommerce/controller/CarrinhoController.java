package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.dto.carrinho.CarrinhoResponseDTO;
import br.com.motta.ecommerce.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CarrinhoResponseDTO> getCarrinhoByLogin(@PathVariable String login) {
        return service.getCarrinhoByLogin(login);
    }

    @PostMapping("/adicionar/{apelido}/{tamanho}")
    public ResponseEntity<ResponseDTO> addProduto(@RequestHeader("Authorization") String token,
                                                  @PathVariable String apelido,
                                                  @PathVariable String tamanho) {
        return service.addProduto(token, apelido, tamanho);
    }

    @DeleteMapping("/deletar/{apelido}/{tamanho}")
    public ResponseEntity<ResponseDTO> deletarProduto(@RequestHeader("Authorization") String token,
                                                      @PathVariable String apelido,
                                                      @PathVariable String tamanho) {
        return service.deletarProdutoCarrinho(token, apelido, tamanho);
    }

    @DeleteMapping("/remover/{apelido}/{tamanho}")
    public ResponseEntity<ResponseDTO> removerProduto(@RequestHeader("Authorization") String token,
                                                      @PathVariable String apelido,
                                                      @PathVariable String tamanho) {
        return service.removerProduto(token, apelido, tamanho);
    }
}
