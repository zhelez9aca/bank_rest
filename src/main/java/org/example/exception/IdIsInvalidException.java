package org.example.exception;

public class IdIsInvalidException extends RuntimeException {
    public IdIsInvalidException(String message) {
        super(message);
    }
}
