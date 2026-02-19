package com.sahil.ledger_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InconsistentDataException.class)
    public ResponseEntity<String> handleInconsistentData(InconsistentDataException ex) {
        
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFunds(InsufficientFundsException ex) {
        return new ResponseEntity<>(ex.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
    }
}