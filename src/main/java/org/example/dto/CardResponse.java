package org.example.dto;

import org.example.enums.CardStatus;

import java.math.BigDecimal;

public record CardResponse(
        String maskedPan,
        String holderName,
        int expiryMonth,
        int expiryYear,
        CardStatus status,
        BigDecimal balance
        ){}
