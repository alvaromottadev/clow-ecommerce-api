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
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ItemCarrinho> itensCarrinho;

    public void addItem(ItemCarrinho item) {
        total += item.getProduto().getPreco() * (1 - item.getProduto().getDesconto());
        itensCarrinho.add(item);
    }

    public void removerItem(ItemCarrinho item){
        itensCarrinho.remove(item);
    }

    public void addTotal(Double valor, Double desconto) {
        total += valor * (1 - desconto);
    }

    public void removeTotal(Integer quantidade, Double valor, Double desconto) {
        total -= (quantidade - 1) * (valor * (1 - desconto));
    }

    public void updateTotal(String produtoId, Double valorAntigo, Double valorNovo) {
        int quantidade = 0;
        for (ItemCarrinho item : itensCarrinho) {
            if (item.getProduto().getId().equals(produtoId)) {
                quantidade++;
            }
        }
        Double carrinhoAntes = quantidade * valorAntigo;
        Double carrinhoDepois = quantidade * valorNovo;
        Double diferenca = carrinhoAntes - carrinhoDepois;
        setTotal(getTotal() - diferenca);
    }

    public void updateTotal(String produtoId) {
        Double totalPreco = 0.0;
        for (ItemCarrinho item : itensCarrinho) {
            if (item.getProduto().getId().equals(produtoId)) {
                totalPreco += item.getQuantidade() * (item.getProduto().getPreco() * (1 - item.getProduto().getDesconto()));
            }
        }
        setTotal(getTotal() - totalPreco);
    }

}
