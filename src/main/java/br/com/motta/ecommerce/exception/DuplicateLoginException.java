package br.com.motta.ecommerce.exception;

public class DuplicateLoginException extends RuntimeException{

    public DuplicateLoginException(String mensagem){
        super(mensagem);
    }

}
