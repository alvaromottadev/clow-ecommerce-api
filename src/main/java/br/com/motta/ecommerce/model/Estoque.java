package br.com.motta.ecommerce.model;

import br.com.motta.ecommerce.exception.NoStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tamanho")
    private String tamanho;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produtoEstoque;

    public void adicionarQuantidade(Integer quantidade){
        setQuantidade(getQuantidade() + quantidade);
    }

    public void removerQuantidade(Integer quantidade){
        Integer quantidadeEstoque = getQuantidade();
        if (quantidadeEstoque != 0) {
            setQuantidade(getQuantidade() - quantidade);
            return;
        }
        throw new NoStockException("O produto não possui estoque.");
    }

    public Estoque(String tamanho, Integer quantidade){
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

}
