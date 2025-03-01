package br.com.motta.ecommerce.dto;

import br.com.motta.ecommerce.model.ItemCarrinho;
import br.com.motta.ecommerce.model.Produto;

import java.util.List;

public record ItemCarrinhoResponseDTO (String nome, String apelido, String descricao, String tamanho, String categoria, List<String> imagemUrl, Double preco, Double desconto, Integer quantidade) {

    public ItemCarrinhoResponseDTO(ItemCarrinho item){

        this(item.getProduto().getNome(),
                item.getProduto().getApelido(),
                item.getProduto().getDescricao(),
                item.getTamanho(),
                item.getProduto().getCategoria(),
                item.getProduto().getImagemUrl(),
                item.getProduto().getPreco(),
                item.getProduto().getDesconto(),
                item.getQuantidade());

    }

}
