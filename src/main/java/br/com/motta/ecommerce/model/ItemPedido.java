package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "produto_id")
//    private Produto produtoPedido;

    @Column(name = "produto_id")
    private String produtoId;

    @Column(name = "produto_nome")
    private String produtoNome;

    @Column(name = "imagem_url")
    private List<String> imagemUrl;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private String tamanho;

    private Integer quantidade;

    private Double preco;

    public ItemPedido(String produtoId, String produtoNome, List<String> imagemUrl, Pedido pedido, String tamanho, Integer quantidade, Double preco){
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.imagemUrl = imagemUrl;
        this.pedido = pedido;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
        this.preco = preco;
    }

}
