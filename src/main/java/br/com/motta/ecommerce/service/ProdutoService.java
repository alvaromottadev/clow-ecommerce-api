package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.DeleteResponseDTO;
import br.com.motta.ecommerce.dto.ProdutoResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos(){
         return ResponseEntity.ok(repository.findAll().stream().map(ProdutoResponseDTO::new).toList());
    }

    public ResponseEntity<ProdutoResponseDTO> getProduto(String apelido){
        Produto produto = repository.findByApelido(apelido);
        if (produto == null){
            throw new NotFoundException("Produto não encontrado.");
        }
        return ResponseEntity.ok(new ProdutoResponseDTO(produto));
    }

    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(Produto produto){
        String apelido = produto.getNome()
                .toLowerCase()
                .replace(" ", "-");
        Produto produtoEncontrado = repository.findByApelido(apelido);

        if (produto.getDesconto() == null){
            produto.setDesconto(0.0);
        }

        //Verificar se o apelido já existe
        while (produtoEncontrado != null){
            Random random = new Random();
            int extra = random.nextInt(50);
            apelido = apelido.concat(extra + "");
            produtoEncontrado = repository.findByApelido(apelido);
        }

        produto.setApelido(apelido);
        return ResponseEntity.status(201).body(new ProdutoResponseDTO(repository.save(produto)));
    }

    public ResponseEntity<DeleteResponseDTO> deletarProduto(String apelido){
        Produto produto = repository.findByApelido(apelido);
        if (produto == null){
            throw new NotFoundException("O produto não foi encontrado.");
        }
        repository.delete(produto);
        return ResponseEntity.ok(new DeleteResponseDTO("O produto foi removido com sucesso."));
    }

}
