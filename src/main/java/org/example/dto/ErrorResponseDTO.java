package org.example.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(Integer status, String message, LocalDateTime timeStamp) {
}
