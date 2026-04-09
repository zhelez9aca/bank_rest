package org.example.mapper;

import org.example.dto.CardBlockRequestResponseDTO;
import org.example.model.CardBlockRequest;
import org.springframework.stereotype.Component;

@Component
public class CardBlockMapper {
    public CardBlockRequestResponseDTO toResponse(CardBlockRequest request){
        return new CardBlockRequestResponseDTO(
                request.getId(),
                request.getCard().getId(),
                request.getUser().getId(),
                request.getStatus(),
                request.getReason(),
                request.getCreatedAt()
        );
    }
}
