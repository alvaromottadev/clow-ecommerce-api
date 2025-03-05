package br.com.motta.ecommerce.exception;

public class SignatureInvalidException extends RuntimeException{

    public SignatureInvalidException(String mensagem){
        super(mensagem);
    }

}
