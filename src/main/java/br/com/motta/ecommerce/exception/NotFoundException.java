package br.com.motta.ecommerce.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String mensagem){
        super(mensagem);
    }

}
