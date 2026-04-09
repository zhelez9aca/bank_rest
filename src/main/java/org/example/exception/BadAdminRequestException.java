package org.example.exception;

public class BadAdminRequestException extends RuntimeException {
    public BadAdminRequestException(String message) {
        super(message);
    }
}
