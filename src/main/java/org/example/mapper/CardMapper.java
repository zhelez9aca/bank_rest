package org.example.mapper;

import org.example.dto.CardResponseDTO;
import org.example.model.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardResponseDTO toResponse(Card entity){
        return new CardResponseDTO("**** **** **** ".concat(entity.getLast4()),
                entity.getHolderName(),
                entity.getExpiryMonth(),
                entity.getExpiryYear(),
                entity.getStatus(),
                entity.getBalance());
    }
}
