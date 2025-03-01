package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.ProdutoAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.ProdutoResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.ItemCarrinho;
import br.com.motta.ecommerce.model.Produto;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.ItemCarrinhoRepository;
import br.com.motta.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {
        return ResponseEntity.ok(repository.findAll().stream().map(ProdutoResponseDTO::new).toList());
    }

    public ResponseEntity<ProdutoResponseDTO> getProduto(String apelido) {
        Produto produto = repository.findByApelido(apelido);
        if (produto == null) {
            throw new NotFoundException("Produto não encontrado.");
        }
        return ResponseEntity.ok(new ProdutoResponseDTO(produto));
    }

    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(Produto produto) {
        String apelido = produto.getNome()
                .toLowerCase()
                .replace(" ", "-");
        Produto produtoEncontrado = repository.findByApelido(apelido);

        if (produto.getDesconto() == null) {
            produto.setDesconto(0.0);
        }

        //Verificar se o apelido já existe
        while (produtoEncontrado != null) {
            Random random = new Random();
            int extra = random.nextInt(50);
            apelido = apelido.concat(extra + "");
            produtoEncontrado = repository.findByApelido(apelido);
        }

        produto.setApelido(apelido);
        return ResponseEntity.status(201).body(new ProdutoResponseDTO(repository.save(produto)));
    }

    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(ProdutoAtualizarRequestDTO data) {
        Optional<Produto> produto = repository.findById(data.id());
        if (!produto.isPresent()) {
            throw new NotFoundException("Produto não encontrado.");
        }
        updateProduto(produto.get(), data);
        return ResponseEntity.ok(new ProdutoResponseDTO(produto.get()));
    }

    public ResponseEntity<ResultDTO> deletarProduto(String apelido) {
        Produto produto = repository.findByApelido(apelido);
        if (produto == null) {
            throw new NotFoundException("O produto não foi encontrado.");
        }
        repository.delete(produto);
        return ResponseEntity.ok(new ResultDTO("O produto foi removido com sucesso."));
    }

    private void updateProduto(Produto produto, ProdutoAtualizarRequestDTO data) {
        produto.setNome(data.nome());
        produto.setApelido(data.apelido());
        produto.setDescricao(data.descricao());
        produto.setImagemUrl(data.imagemUrl());
        produto.setPreco(data.preco());
        produto.setDesconto(data.desconto());

        repository.save(produto);
    }

}
