package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "total")
    private Double total;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCarrinho> itensCarrinho;

    public void addItem(ItemCarrinho item){
        total += item.getProduto().getPreco() * (1 - item.getProduto().getDesconto());
        itensCarrinho.add(item);
    }

    public void addTotal(Double valor, Double desconto){
        total += valor * (1 - desconto);
    }

    public void removeTotal(Integer quantidade, Double valor, Double desconto){
        total -= (quantidade - 1) * (valor * (1 - desconto));
    }

//    public Double getTotal(){
//        for (ItemCarrinho itemCarrinho : itensCarrinho){
//            total += itemCarrinho.getProduto().getPreco() * (1 - itemCarrinho.getProduto().getDesconto());
//        }
//        return total;
//    }

}
