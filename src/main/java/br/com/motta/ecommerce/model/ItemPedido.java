package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produtoPedido;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private String tamanho;

    private Integer quantidade;

    private Double preco;

    public ItemPedido(Produto produto, Pedido pedido, String tamanho, Integer quantidade, Double preco){
        this.produtoPedido = produto;
        this.pedido = pedido;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
        this.preco = preco;
    }

}
