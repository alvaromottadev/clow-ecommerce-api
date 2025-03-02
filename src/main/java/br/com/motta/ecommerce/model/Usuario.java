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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 50, name = "username")
    private String username;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "saldo", columnDefinition = "Float default 0")
    private Double saldo;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Carrinho carrinho;

    @OneToMany(mappedBy = "usuarioPedido", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    public Usuario(String username, String login, String password, Double saldo){

        this.username = username;
        this.login = login;
        this.password = password;
        this.saldo = saldo;

    }

}
