package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "apelido", unique = true)
    private String apelido;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "imagem")
    private List<String> imagemUrl;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "desconto")
    private Double desconto;

    @OneToMany(mappedBy = "produtoEstoque", cascade = CascadeType.ALL)
    private List<Estoque> estoques = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto", fetch = FetchType.LAZY)
    private List<ItemCarrinho> itensCarrinho;

    @OneToMany(mappedBy = "produtoPedido", fetch = FetchType.LAZY)
    private List<ItemPedido> itensPedido;

    public void addEstoque(Estoque estoque){
        estoques.add(estoque);
    }

    public Produto(String nome, String descricao, String categoria, List<String> imagemUrl, Double preco, Double desconto){

        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.imagemUrl = imagemUrl;
        this.preco = preco;
        this.desconto = desconto;

    }

}
