package org.example.exception;

public class CardBlockRequestNotFoundException extends RuntimeException {
    public CardBlockRequestNotFoundException(String message) {
        super(message);
    }
}
