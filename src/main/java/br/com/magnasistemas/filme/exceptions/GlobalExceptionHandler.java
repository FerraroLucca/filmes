package br.com.magnasistemas.filme.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> erros = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> erros.add(violation.getMessage()));
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        List<String> erros = new ArrayList<>();
        ex.getAllErrors().forEach(error -> erros.add(error.getDefaultMessage()));
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex) {
        List<String> erros = new ArrayList<>();
        erros.add(ex.getMessage());
        return new ResponseEntity<>(erros, HttpStatus.NOT_FOUND);
    }
    
    
}
