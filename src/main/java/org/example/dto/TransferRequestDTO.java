package org.example.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(Long fromCardId, Long toCardId, BigDecimal amount) {
}
