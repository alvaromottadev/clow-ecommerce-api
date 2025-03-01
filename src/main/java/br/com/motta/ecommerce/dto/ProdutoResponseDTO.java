package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.model.Produto;

import java.util.List;

public record ProdutoResponseDTO(String nome, String apelido, String descricao, List<String> tamanhos, String categoria, List<String> imagemUrl, Double preco, Double desconto) {

    public ProdutoResponseDTO(Produto produto){

        this(produto.getNome(), produto.getApelido(), produto.getDescricao(), produto.getTamanhos(), produto.getCategoria(), produto.getImagemUrl(), produto.getPreco(), produto.getDesconto());

    }

}
