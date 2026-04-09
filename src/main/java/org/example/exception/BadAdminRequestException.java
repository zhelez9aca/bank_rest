package org.example.exception;

public class BadAdminRequest extends RuntimeException {
    public BadAdminRequest(String message) {
        super(message);
    }
}
