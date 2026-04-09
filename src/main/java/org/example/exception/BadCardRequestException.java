package org.example.exception;

public class BadCardRequestException extends RuntimeException {
    public BadCardRequestException(String message) {
        super(message);
    }
}
