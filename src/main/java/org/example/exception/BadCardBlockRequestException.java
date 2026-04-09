package org.example.exception;

public class BadCardBlockRequestException extends RuntimeException {
    public BadCardBlockRequestException(String message) {
        super(message);
    }
}
