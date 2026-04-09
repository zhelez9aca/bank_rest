package org.example.dto;

import org.example.enums.CardBlockStatusEnum;

import java.time.LocalDateTime;

public record CardBlockRequestResponse (Long id,
                                        Long cardId,
                                        Long userId,
                                        CardBlockStatusEnum status,
                                        String reason,
                                        LocalDateTime createdAt){
}
