package br.com.motta.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @Column(name = "tamanhos")
    private List<String> tamanhos;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "imagem")
    private List<String> imagemUrl;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "desconto")
    private Double desconto;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto", fetch = FetchType.LAZY)
    private List<ItemCarrinho> itensCarrinho;

    public Produto(String nome, String descricao, List<String> tamanhos, String categoria, List<String> imagemUrl, Double preco, Double desconto){

        this.nome = nome;
        this.descricao = descricao;
        this.tamanhos = tamanhos;
        this.categoria = categoria;
        this.imagemUrl = imagemUrl;
        this.preco = preco;
        this.desconto = desconto;

    }

}
