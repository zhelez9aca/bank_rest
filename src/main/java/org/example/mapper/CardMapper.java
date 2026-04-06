package org.example.mapper;

import org.example.dto.CardResponse;
import org.example.model.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardResponse toResponse(Card entity){
        return new CardResponse("**** **** **** ".concat(entity.getLast4()),
                entity.getHolderName(),
                entity.getExpiryMonth(),
                entity.getExpiryYear(),
                entity.getStatus(),
                entity.getBalance());
    }
}
