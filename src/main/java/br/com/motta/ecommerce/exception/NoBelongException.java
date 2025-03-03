package br.com.motta.ecommerce.exception;

public class NoBelongException extends RuntimeException {

    public NoBelongException(String mensagem){
        super(mensagem);
    }

}
