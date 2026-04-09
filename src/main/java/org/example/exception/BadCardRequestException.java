package org.example.exception;

public class BadCardRequest extends RuntimeException {
    public BadCardRequest(String message) {
        super(message);
    }
}
