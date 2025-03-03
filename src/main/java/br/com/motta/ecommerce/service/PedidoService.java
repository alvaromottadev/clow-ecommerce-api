package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.dto.pedido.EnderecoRequestDTO;
import br.com.motta.ecommerce.dto.pedido.PedidoResponseDTO;
import br.com.motta.ecommerce.exception.EmptyException;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.infra.security.JwtTokenUtil;
import br.com.motta.ecommerce.model.*;
import br.com.motta.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<PedidoResponseDTO> findPedido(String token, Long id){
        String login = JwtTokenUtil.getLogin(token);
        Pedido pedido = repository.findByIdAndUsuarioPedidoLogin(id, login).orElseThrow(() -> new NotFoundException("Pedido não encontrado ou não pertence a você."));
        return ResponseEntity.ok(new PedidoResponseDTO(pedido));
    }

    public ResponseEntity<List<PedidoResponseDTO>> findAllPedidosByUsuarioId(String usuarioId){
        List<Pedido> pedidos = repository.findAllByUsuarioPedidoId(usuarioId);
        if (pedidos.isEmpty()) {
            throw new NotFoundException("Esse usuário não possui pedidos.");
        }
        return ResponseEntity.ok().body(pedidos.stream().map(PedidoResponseDTO::new).toList());
    }

    @Transactional
    public void efetuarPedido(String token, EnderecoRequestDTO endereco){

        String login = JwtTokenUtil.getLogin(token);
        String enderecoString = endereco.logradouro() + ", " + endereco.numero() + ", " + endereco.bairro();
        if (endereco.complemento() != null){
            enderecoString += ", " + endereco.complemento();
        }
        enderecoString += ", " + endereco.cidade() + ", " + endereco.estado() + ", " + endereco.cep();

        Carrinho carrinho = carrinhoRepository.findByUsuarioLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        if (carrinho.getItensCarrinho().isEmpty()){
            throw new EmptyException("Carrinho vazio.");
        }
        Pedido pedido = new Pedido(carrinho.getUsuario(), enderecoString, carrinho.getTotal());
        repository.save(pedido);
        for (ItemCarrinho item : carrinho.getItensCarrinho()){
            Double preco = item.getProduto().getPreco() * (1 - item.getProduto().getDesconto());
            ItemPedido itemPedido = new ItemPedido(item.getProduto().getId(), item.getProduto().getNome(), item.getProduto().getImagemUrl(), pedido, item.getTamanho(), item.getQuantidade(), preco);
            pedido.addItem(itemPedido);
            itemPedidoRepository.save(itemPedido);
        }

        updateEstoques(pedido.getItensPedido());
        carrinho.getItensCarrinho().clear();
        carrinho.setTotal(0.0);

        carrinhoRepository.save(carrinho);
        repository.save(pedido);

        emailService.sendEmail(new EmailDTO(carrinho.getUsuario().getLogin(), "Compra Aprovada - Ecommerce API", "Obrigado por comprar com a gente! Seu pedido está sendo preparado com carinho!"));

    }

    private void updateEstoques(List<ItemPedido> itensPedido){
        for (ItemPedido item : itensPedido){
            Estoque estoque = estoqueRepository.findByTamanhoAndProdutoEstoqueId(item.getTamanho(), item.getProdutoId()).orElseThrow(() -> new NotFoundException("Estoque não encontrado. !!!"));
            estoque.removerQuantidade(item.getQuantidade());
        }
    }

}
