package org.example.dto;

import org.example.enums.CardStatusEnum;

import java.math.BigDecimal;

public record CardResponseDTO(
        String maskedPan,
        String holderName,
        int expiryMonth,
        int expiryYear,
        CardStatusEnum status,
        BigDecimal balance
) {
}
