package org.example.service;

import lombok.AllArgsConstructor;
import org.example.enums.CardBlockStatusEnum;
import org.example.enums.CardStatusEnum;
import org.example.exception.BadCardBlockRequestException;
import org.example.exception.CardNotFoundException;
import org.example.exception.InvalidTokenException;
import org.example.exception.UserNotFoundException;
import org.example.model.CardBlockRequest;
import org.example.repository.CardBlockRepository;
import org.example.repository.CardRepository;
import org.example.repository.UserRepository;
import org.example.security.JWToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCardBlockRequestService {
    private final CardBlockRepository cardBlockRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final JWToken jwToken;

    public void blockRequest(Long cardId, String token, String reason){
        if (!jwToken.isValid(token)){
            throw new InvalidTokenException("Token is invalid");
        }
        if (cardBlockRepository.existsByCardIdAndStatus(cardId, CardBlockStatusEnum.PENDING)) {
            throw new BadCardBlockRequestException("Block request already pending");
        }
        var userLogin = jwToken.extractLogin(token);
        var user = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("User is not found: " + userLogin));
        var card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card is not found: " + cardId));
        if (card.getStatus() != CardStatusEnum.ACTIVE) {
            throw new BadCardBlockRequestException("Card is not active");
        }
        if (!card.getUser().equals(user)) {
            throw new BadCardBlockRequestException("This is not your card");
        }
        var blockRequest = new CardBlockRequest();
        blockRequest.setStatus(CardBlockStatusEnum.PENDING);
        blockRequest.setUser(user);
        blockRequest.setCard(card);
        if (reason != null) {
            blockRequest.setReason(reason);
        }
        cardBlockRepository.save(blockRequest);
    }
}
