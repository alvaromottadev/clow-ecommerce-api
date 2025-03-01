package br.com.motta.ecommerce.exception;

public class NoStockException extends RuntimeException {

    public NoStockException(String mensagem){
        super(mensagem);
    }

}
