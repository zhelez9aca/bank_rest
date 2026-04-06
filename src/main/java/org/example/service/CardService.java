package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.CardResponse;
import org.example.exception.InvalidTokenException;
import org.example.mapper.CardMapper;
import org.example.model.Card;
import org.example.repository.CardRepository;
import org.example.security.JWToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
    private  final CardRepository CardRepository;
    private  final JWToken jwToken;
    private final CardMapper CardMapper;
    public List<CardResponse> getCards(String token){
        if (!jwToken.isValid(token)){
            throw new InvalidTokenException("Token is invalid");
        }
        var userId = jwToken.extractUserId(token);
        var CardList = CardRepository.findByUserId(userId);
        var CardResp = new ArrayList<CardResponse>();
        for (Card Card: CardList ){
            CardResp.add(CardMapper.toResponse(Card));
        }
        return CardResp;
    }
}
