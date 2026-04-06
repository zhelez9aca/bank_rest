package org.example.exception;

public class BadTransferRequestException extends RuntimeException {
    public BadTransferRequestException(String message) {
        super(message);
    }
}
