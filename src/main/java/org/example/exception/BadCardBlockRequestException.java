package org.example.exception;

public class BadCardBlockRequest extends RuntimeException {
    public BadCardBlockRequest(String message) {
        super(message);
    }
}
