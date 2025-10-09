package org.example.backend.exceptions;

public class EmptyProductNameException extends RuntimeException {
    public EmptyProductNameException(String message) {
        super(message);
    }
}
