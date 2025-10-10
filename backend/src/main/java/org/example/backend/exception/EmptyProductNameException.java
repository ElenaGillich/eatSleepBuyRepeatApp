package org.example.backend.exception;

public class EmptyProductNameException extends RuntimeException {
    public EmptyProductNameException(String message) {
        super(message);
    }
}
