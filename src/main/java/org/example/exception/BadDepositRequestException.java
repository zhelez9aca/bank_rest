package org.example.exception;

public class BadDepositRequestException extends RuntimeException {
    public BadDepositRequestException(String message) {
        super(message);
    }
}
