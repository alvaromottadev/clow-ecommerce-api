package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.dto.ResponseDTO;
import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Estoque;
import br.com.motta.ecommerce.repository.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository repository;

    @Transactional
    public ResponseEntity<ResponseDTO> getEstoque(String apelido, String tamanho){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueApelido(tamanho, apelido).orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
        return ResponseEntity.ok(new ResponseDTO("Quantidade em estoque: " + estoque.getQuantidade()));
    }

    @Transactional
    public ResponseEntity<ResponseDTO> adicionarEstoque(String apelido, String tamanho, Integer quantidade){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueApelido(tamanho, apelido).orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
        estoque.adicionarQuantidade(quantidade);
        repository.save(estoque);
        return ResponseEntity.ok(new ResponseDTO("Foram adicionados " + quantidade + " itens no estoque do produto '" + estoque.getProdutoEstoque().getNome() + "' com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseDTO> removerEstoque(String apelido, String tamanho, Integer quantidade){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueApelido(tamanho, apelido).orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
        estoque.removerQuantidade(quantidade);
        repository.save(estoque);
        return ResponseEntity.ok(new ResponseDTO("Foram removidos " + quantidade + " itens no estoque do produto '" + estoque.getProdutoEstoque().getNome() + "' com sucesso."));
    }

}
