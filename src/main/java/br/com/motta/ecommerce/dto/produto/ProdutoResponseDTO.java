package br.com.motta.ecommerce.dto.produto;

import br.com.motta.ecommerce.dto.estoque.EstoqueResponseDTO;
import br.com.motta.ecommerce.model.Produto;

import java.util.List;

public record ProdutoResponseDTO(String id, String nome, String apelido, String descricao, List<EstoqueResponseDTO> estoques, String categoria, List<String> imagemUrl, Double preco, Double desconto) {

    public ProdutoResponseDTO(Produto produto){

        this(produto.getId(),
                produto.getNome(),
                produto.getApelido(),
                produto.getDescricao(),
                produto.getEstoques().stream().map(EstoqueResponseDTO::new).toList(),
                produto.getCategoria(),
                produto.getImagemUrl(),
                produto.getPreco(),
                produto.getDesconto());

    }

}
