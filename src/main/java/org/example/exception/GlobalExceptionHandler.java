package org.example.exception;

import org.example.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadTransferRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadTransferRequestException(BadTransferRequestException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCardNotFoundException(CardNotFoundException ex){
        return build(404, ex.getMessage());
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex){
        return build(409, ex.getMessage());
    }
    @ExceptionHandler(IdIsInvalidException.class)
    public ResponseEntity<ErrorResponseDTO> handleCardNotFoundException(IdIsInvalidException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(IncorrectLoginOrPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectLoginOrPasswordException(IncorrectLoginOrPasswordException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(IncorrectRoleException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectRoleException(IncorrectRoleException ex){
        return build(403, ex.getMessage());
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTokenException(InvalidTokenException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException ex){
        return build(404, ex.getMessage());
    }
    @ExceptionHandler(BlockedUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleBlockedUser(BlockedUserException ex){
        return build(403, ex.getMessage());
    }
    @ExceptionHandler(CryptoException.class)
    public ResponseEntity<ErrorResponseDTO> handleCryptoException(CryptoException ex){
        return build(500, ex.getMessage());
    }
    @ExceptionHandler(BadAdminRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadAdminRequest(BadAdminRequestException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(BadCardRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCardRequest(BadCardRequestException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(BadDepositRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadDepositRequest(BadDepositRequestException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(BadCardBlockRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCardBlockRequest(BadCardBlockRequestException ex){
        return build(400, ex.getMessage());
    }
    @ExceptionHandler(CardBlockRequestNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCardBlockRequestNotFound(CardBlockRequestNotFoundException ex){
        return build(404, ex.getMessage());
    }

    private ResponseEntity<ErrorResponseDTO> build(int status, String message) {
        var resp = new ErrorResponseDTO(status, message, LocalDateTime.now());
        return ResponseEntity.status(status).body(resp);
    }
}
