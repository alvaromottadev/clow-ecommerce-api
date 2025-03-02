package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioPedido;

    private LocalDateTime dataPedido;

    private Double total;

//    @ManyToOne
//    @JoinColumn(name = "endereco_id")
    private String endereco;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itensPedido;

    public void addItem(ItemPedido item){
        itensPedido.add(item);
    }

    public Pedido(Usuario usuario, String endereco, Double total){
        this.itensPedido = new ArrayList<>();
        this.usuarioPedido = usuario;
        this.endereco = endereco;
        this.total = total;
        this.dataPedido = LocalDateTime.now();
    }

}
