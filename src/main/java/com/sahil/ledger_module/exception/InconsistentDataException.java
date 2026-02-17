package com.sahil.ledger_module.exception;

public class InconsistentDataException extends RuntimeException {
    public InconsistentDataException(String message) {
        super(message);
    }
}