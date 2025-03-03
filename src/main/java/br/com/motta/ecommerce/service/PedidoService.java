package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.EmailDTO;
import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.dto.pedido.EnderecoRequestDTO;
import br.com.motta.ecommerce.dto.pedido.PedidoResponseDTO;
import br.com.motta.ecommerce.exception.EmptyException;
import br.com.motta.ecommerce.exception.NoBelongException;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.infra.security.JwtTokenUtil;
import br.com.motta.ecommerce.model.*;
import br.com.motta.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<PedidoResponseDTO> findPedido(String token, Long id){
        String login = JwtTokenUtil.getLogin(token);
        Optional<Pedido> pedido = repository.findById(id);
        if (pedido.isPresent()){
            Cliente clientePedido = pedido.get().getClientePedido();
            Cliente cliente = clienteRepository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
            if (cliente.getRole().equals(ClienteRole.ADMIN) || cliente.getLogin().equals(clientePedido.getLogin())){
                return ResponseEntity.ok(new PedidoResponseDTO(pedido.get()));
            }
            throw new NoBelongException("Esse pedido não pertence a você.");
        }
        throw new NotFoundException("Pedido não encontrado.");
    }

    public ResponseEntity<List<PedidoResponseDTO>> findAllPedidosByClienteLogin(String login){
        List<Pedido> pedidos = repository.findAllByClientePedidoLogin(login);
        if (pedidos.isEmpty()) {
            throw new NotFoundException("Esse usuário não possui pedidos.");
        }
        return ResponseEntity.ok().body(pedidos.stream().map(PedidoResponseDTO::new).toList());
    }

    @Transactional
    public ResponseEntity<?> efetuarPedido(String token, EnderecoRequestDTO endereco){

        String login = JwtTokenUtil.getLogin(token);
        String enderecoString = endereco.logradouro() + ", " + endereco.numero() + ", " + endereco.bairro();
        if (endereco.complemento() != null){
            enderecoString += ", " + endereco.complemento();
        }
        enderecoString += ", " + endereco.cidade() + ", " + endereco.estado() + ", " + endereco.cep();

        Carrinho carrinho = carrinhoRepository.findByClienteLogin(login).orElseThrow(() -> new NotFoundException("Carrinho não encontrado."));
        if (carrinho.getItensCarrinho().isEmpty()){
            throw new EmptyException("Carrinho vazio.");
        }
        Pedido pedido = new Pedido(carrinho.getCliente(), enderecoString, carrinho.getTotal());
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

        String bodyEmail = "Obrigado por comprar com a gente! Seu pedido está sendo preparado com carinho! \nID do Pedido: " + pedido.getId() + "\n\nProdutos: ";
        for (ItemPedido itens : pedido.getItensPedido()){
            bodyEmail = bodyEmail.concat("\n" + itens.getProdutoNome() + " - " + itens.getTamanho());
        }
        bodyEmail = bodyEmail.concat("\nTotal do Pedido: R$" + String.format("%.2f", pedido.getTotal()));
        emailService.sendEmail(new EmailDTO(carrinho.getCliente().getLogin(), "Compra Aprovada - Clow Ecommerce API", bodyEmail));

        return ResponseEntity.ok(new ResponseDTO("Obrigado! Pedido efetuado com sucesso!"));

    }

    private void updateEstoques(List<ItemPedido> itensPedido){
        for (ItemPedido item : itensPedido){
            Estoque estoque = estoqueRepository.findByTamanhoAndProdutoEstoqueId(item.getTamanho(), item.getProdutoId()).orElseThrow(() -> new NotFoundException("Estoque não encontrado. !!!"));
            estoque.removerQuantidade(item.getQuantidade());
        }
    }

}
