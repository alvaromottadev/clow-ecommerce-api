package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_estoque")
public class ItemEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estoque_id", nullable = false)
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produtoEstoque;

    public ItemEstoque(Estoque estoque, Produto produto){
        this.estoque = estoque;
        this.produtoEstoque = produto;
    }

}
