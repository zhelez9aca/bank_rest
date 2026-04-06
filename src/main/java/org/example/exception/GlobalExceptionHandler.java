package org.example.exception;

import org.example.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadTransferRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadTransferRequestException(BadTransferRequestException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCardNotFoundException(CardNotFoundException ex){
        return build(404, ex.getMessage());
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex){
        return build(409, ex.getMessage());
    }
    @ExceptionHandler(IdIsInvalidException.class)
    public ResponseEntity<ErrorResponse> handleCardNotFoundException(IdIsInvalidException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(IncorrectLoginOrPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectLoginOrPasswordException(IncorrectLoginOrPasswordException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(IncorrectRoleException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectRoleException(IncorrectRoleException ex){
        return build(403, ex.getMessage());
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex){
        return build(400, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> build(int status, String message) {
        var resp = new ErrorResponse(status, message, LocalDateTime.now());
        return ResponseEntity.status(status).body(resp);
    }
}
