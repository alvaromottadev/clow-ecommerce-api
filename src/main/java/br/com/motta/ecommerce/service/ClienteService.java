package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResultDTO;
import br.com.motta.ecommerce.dto.cliente.ClienteAtualizarRequestDTO;
import br.com.motta.ecommerce.dto.cliente.ClienteResponseDTO;
import br.com.motta.ecommerce.exception.DuplicateLoginException;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.infra.security.JwtTokenUtil;
import br.com.motta.ecommerce.model.Carrinho;
import br.com.motta.ecommerce.model.Cliente;
import br.com.motta.ecommerce.repository.CarrinhoRepository;
import br.com.motta.ecommerce.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes(){
        return ResponseEntity.ok(repository.findAll().stream().map(ClienteResponseDTO::new).toList());
    }

    public ResponseEntity<ClienteResponseDTO> getPerfil(String token){
        String login = JwtTokenUtil.getLogin(token);
        Cliente cliente = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
        return ResponseEntity.ok(new ClienteResponseDTO(cliente));
    }

    public ResponseEntity<ClienteResponseDTO> getCliente(String login){
        Cliente cliente = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
        return ResponseEntity.ok(new ClienteResponseDTO(cliente));
    }

    @Transactional
    public ResponseEntity<ResultDTO> registerCliente(Cliente cliente){
        if (repository.existsByLogin(cliente.getLogin())){
            throw new DuplicateLoginException("Já existe um cliente com esse email cadastrado.");
        }
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        repository.save(cliente);
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinho.setTotal(0.0);
        carrinhoRepository.save(carrinho);
        return ResponseEntity.status(201).body(new ResultDTO("Cliente cadastrado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> atualizarPerfil(String token, ClienteAtualizarRequestDTO data){
        String login = JwtTokenUtil.getLogin(token);
        Cliente cliente = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
        updateCliente(cliente, data);
        return ResponseEntity.ok(new ResultDTO("Perfil atualizado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> atualizarCliente(String login, ClienteAtualizarRequestDTO data){
        Cliente cliente = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
        updateCliente(cliente, data);
        return ResponseEntity.ok(new ResultDTO("Cliente atualizado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> deletarPerfil(String token){
        String login = JwtTokenUtil.getLogin(token);
        Cliente cliente = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
        repository.delete(cliente);
        return ResponseEntity.ok(new ResultDTO("Cliente deletado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResultDTO> deletarCliente(String login){
        Cliente cliente = repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Cliente não encontrado."));
        repository.delete(cliente);
        return ResponseEntity.ok(new ResultDTO("Cliente deletado com sucesso."));
    }

    private void updateCliente(Cliente cliente, ClienteAtualizarRequestDTO data){
        cliente.setUsername(data.username());
        cliente.setLogin(data.login());
        cliente.setPassword(passwordEncoder.encode(data.password()));
        repository.save(cliente);
    }

}
