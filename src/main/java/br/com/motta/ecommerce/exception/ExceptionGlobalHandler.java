package br.com.motta.ecommerce.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleArgumentsNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put("error", error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        return new ResponseEntity<>(new ErrorMessage("Já existe um usuário com esse email."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyException.class)
    public ResponseEntity<?> handleEmptyException(EmptyException exception){
        return new ResponseEntity<>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
