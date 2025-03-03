package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.*;
import br.com.motta.ecommerce.dto.estoque.EstoqueRequestDTO;
import br.com.motta.ecommerce.dto.produto.ProdutoAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.produto.ProdutoRequestDTO;
import br.com.motta.ecommerce.dto.produto.ProdutoResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.*;
import br.com.motta.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos() {
        return ResponseEntity.ok(repository.findAll().stream().map(ProdutoResponseDTO::new).toList());
    }

    public ResponseEntity<ProdutoResponseDTO> getProduto(String apelido) {
        Produto produto = repository.findByApelido(apelido).orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        return ResponseEntity.ok(new ProdutoResponseDTO(produto));
    }

    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(ProdutoRequestDTO data) {
        Produto produtoCriado = new Produto(data.nome(), data.descricao(), data.categoria(), data.imagemUrl(), data.preco(), data.desconto());
        String apelido = produtoCriado.getNome()
                .toLowerCase()
                .replace(" ", "-");
        Produto produtoEncontrado = repository.findByApelido(apelido).orElse(null);

        if (produtoCriado.getDesconto() == null) {
            produtoCriado.setDesconto(0.0);
        }

        for (EstoqueRequestDTO produto : data.estoques()){
            Estoque estoque = new Estoque();
            estoque.setTamanho(produto.tamanho());
            estoque.setQuantidade(produto.quantidade());
            estoque.setProdutoEstoque(produtoCriado);
            produtoCriado.addEstoque(estoque);
        }

        //Verificar se o apelido já existe
        while (produtoEncontrado != null) {
            Random random = new Random();
            int extra = random.nextInt(50);
            apelido = apelido.concat(extra + "");
            produtoEncontrado = repository.findByApelido(apelido).orElse(null);
        }

        produtoCriado.setApelido(apelido);

        return ResponseEntity.status(201).body(new ProdutoResponseDTO(repository.save(produtoCriado)));
    }

    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(String apelido, ProdutoAtualizarRequestDTO data) {

        Produto produto = repository.findByApelido(apelido).orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        List<Carrinho> carrinhos = carrinhoRepository.findAllByItensCarrinhoProdutoId(produto.getId());
        Double valorAntigo = produto.getPreco() * (1 - produto.getDesconto());
        Double valorNovo = data.preco() * (1 - data.desconto());
        for (Carrinho carrinho : carrinhos){
            carrinho.updateTotal(produto.getId(), valorAntigo, valorNovo);
        }
        updateProduto(produto, data);
        return ResponseEntity.ok(new ProdutoResponseDTO(produto));
    }

    public ResponseEntity<ResultDTO> deletarProduto(String apelido) {
        Produto produto = repository.findByApelido(apelido).orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        List<Carrinho> carrinhos = carrinhoRepository.findAllByItensCarrinhoProdutoId(produto.getId());
        for (Carrinho carrinho : carrinhos){
            carrinho.updateTotal(produto.getId());
            carrinhoRepository.save(carrinho);
        }
        repository.delete(produto);
        return ResponseEntity.ok(new ResultDTO("O produto foi deletado com sucesso."));
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
