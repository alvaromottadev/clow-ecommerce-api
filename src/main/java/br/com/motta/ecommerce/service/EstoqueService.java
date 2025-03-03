package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.estoque.EstoqueAtualizadoResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Estoque;
import br.com.motta.ecommerce.repository.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository repository;

    @Transactional
    public ResponseEntity<EstoqueAtualizadoResponseDTO> adicionarEstoque(String apelido, String tamanho, Integer quantidade){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueApelido(tamanho, apelido).orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
        estoque.adicionarQuantidade(quantidade);
        repository.save(estoque);
        return new ResponseEntity<>(new EstoqueAtualizadoResponseDTO(estoque), HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity<EstoqueAtualizadoResponseDTO> removerEstoque(String apelido, String tamanho, Integer quantidade){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueApelido(tamanho, apelido).orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
        estoque.removerQuantidade(quantidade);
        repository.save(estoque);
        return new ResponseEntity<>(new EstoqueAtualizadoResponseDTO(estoque), HttpStatus.ACCEPTED);
    }

}
