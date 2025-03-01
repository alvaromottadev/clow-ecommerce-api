package br.com.motta.ecommerce.service;

import br.com.motta.ecommerce.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public void efetuarPedido(String userId){

    }

}
