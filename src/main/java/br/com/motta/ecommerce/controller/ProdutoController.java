package br.com.motta.ecommerce.controller;

import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.dto.produto.ProdutoAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.produto.ProdutoRequestDTO;
import br.com.motta.ecommerce.dto.produto.ProdutoResponseDTO;
import br.com.motta.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@Validated @RequestBody ProdutoRequestDTO data) {
        return service.cadastrarProduto(data);
    }

    @PutMapping("/atualizar/{apelido}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable String apelido, @Validated @RequestBody ProdutoAtualizarRequestDTO data){
        return service.atualizarProduto(apelido, data);
    }

    @DeleteMapping("/deletar/{apelido}")
    public ResponseEntity<ResponseDTO> deletarProduto(@PathVariable String apelido){
        return service.deletarProduto(apelido);
    }

}
