package br.com.motta.ecommerce.exception;

public class EmptyException extends RuntimeException{

    public EmptyException(String mensagem){
        super(mensagem);
    }

}
