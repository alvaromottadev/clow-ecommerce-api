package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "item_carrinho")
@NoArgsConstructor
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private Integer quantidade;

    private String tamanho;

    public ItemCarrinho(Carrinho carrinho, Produto produto, Integer quantidade, String tamanho){

        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
        this.tamanho = tamanho;

    }

}
