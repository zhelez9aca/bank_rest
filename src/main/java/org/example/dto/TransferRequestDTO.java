package org.example.dto;

import java.math.BigDecimal;

public record TransferRequest(Long fromCardId, Long toCardId, BigDecimal amount) {
}
