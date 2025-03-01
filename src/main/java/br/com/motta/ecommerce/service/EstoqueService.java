package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.exception.NotFoundException;
import br.com.motta.ecommerce.model.Estoque;
import br.com.motta.ecommerce.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository repository;

    public void adicionarEstoque(String id, String tamanho, Integer quantidade){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueId(tamanho, id);
        if (estoque == null){
            throw new NotFoundException("Estoque não encontrado.");
        }
        estoque.adicionarQuantidade(quantidade);
        repository.save(estoque);
    }

    public void removerEstoque(String id, String tamanho, Integer quantidade){
        Estoque estoque = repository.findByTamanhoAndProdutoEstoqueId(tamanho, id);
        if (estoque == null){
            throw new NotFoundException("Estoque não encontrado.");
        }
        estoque.removerQuantidade(quantidade);
        repository.save(estoque);
    }

}
