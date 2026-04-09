package org.example.exception;

public class BadDepositRequest extends RuntimeException {
    public BadDepositRequest(String message) {
        super(message);
    }
}
