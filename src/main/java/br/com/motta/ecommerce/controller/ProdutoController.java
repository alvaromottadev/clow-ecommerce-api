package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.ProdutoAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.ProdutoRequestDTO;
import br.com.motta.ecommerce.dto.ProdutoResponseDTO;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {
        return service.getAllProdutos();
    }

    @GetMapping("/{apelido}")
    public ResponseEntity<ProdutoResponseDTO> getProduto(@PathVariable String apelido) {
        return service.getProduto(apelido);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Validated @RequestBody ProdutoRequestDTO produto) {
        Produto produtoCriado = new Produto(produto.nome(), produto.descricao(), produto.imagemUrl(), produto.preco(), produto.desconto());
        return service.cadastrarProduto(produtoCriado);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@Validated @RequestBody ProdutoAtualizarRequestDTO data){
        return service.atualizarProduto(data);
    }

    @DeleteMapping("/deletar/{apelido}")
    public ResponseEntity<ResultDTO> deletarProduto(@PathVariable String apelido){
        return service.deletarProduto(apelido);
    }

}
