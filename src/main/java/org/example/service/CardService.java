package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.CardResponseDTO;
import org.example.enums.CardStatusEnum;
import org.example.exception.*;
import org.example.mapper.CardMapper;
import org.example.model.Card;
import org.example.repository.CardRepository;
import org.example.repository.UserRepository;
import org.example.security.JWToken;
import org.example.util.EncryptionUtil;
import org.example.util.PanGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final JWToken jwToken;
    private final CardMapper cardMapper;
    private final EncryptionUtil encryptionUtil;
    private final PanGenerator panGenerator;

    public Page<CardResponseDTO> getCards(String token, int page, int size, CardStatusEnum statusEnum) {
        if (!jwToken.isValid(token)) {
            throw new InvalidTokenException("Token is invalid");
        }
        var userId = jwToken.extractUserId(token);
        var pageable = PageRequest.of(page, size);
        if (statusEnum == null) {
            return cardRepository.findByUserId(userId, pageable).map(cardMapper::toResponse);
        }
        return cardRepository.findByUserIdAndStatus(userId, statusEnum, pageable).map(cardMapper::toResponse);
    }

    public CardResponseDTO createCard(String token, String holderName) {
        var now = LocalDateTime.now();
        if (!jwToken.isValid(token)) {
            throw new InvalidTokenException("Token is invalid");
        }
        var userId = jwToken.extractUserId(token);
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User is not found"));

        var card = new Card();
        card.setStatus(CardStatusEnum.ACTIVE);
        card.setBalance(BigDecimal.ZERO);
        card.setUser(user);
        var pan = panGenerator.randomCardNumber();
        card.setLast4(pan.substring(pan.length() - 4));
        var encryptedPan = encryptionUtil.encrypt(pan);
        card.setEncryptedPan(encryptedPan);
        card.setExpiryMonth(now.getMonthValue());
        card.setExpiryYear(now.getYear() + 3);
        card.setHolderName(holderName);
        cardRepository.save(card);
        return cardMapper.toResponse(card);
    }

    public void deposit(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadDepositRequestException("Amount must be more than zero");
        }
        var card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card is not found: " + id));
        if (card.getStatus() != CardStatusEnum.ACTIVE) {
            throw new BadDepositRequestException("Card is not active");
        }
        card.setBalance(card.getBalance().add(amount));
        cardRepository.save(card);
    }

    public Page<CardResponseDTO> getAllCards(int page, int size, CardStatusEnum status) {
        var pageable = PageRequest.of(page, size);
        if (status == null) {
            return cardRepository.findAll(pageable).map(cardMapper::toResponse);
        }
        return cardRepository.findByStatus(status, pageable).map(cardMapper::toResponse);
    }

    public void block(Long id) {
        var card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card with id " + id + " is not found"));
        if (card.getStatus() == CardStatusEnum.BLOCKED) {
            throw new BadCardRequestException("Card has already blocked");
        }
        card.setStatus(CardStatusEnum.BLOCKED);
        cardRepository.save(card);
    }

    public void activate(Long id) {
        var card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card with id " + id + " is not found"));
        if (card.getStatus() == CardStatusEnum.ACTIVE) {
            throw new BadCardRequestException("Card has already activated");
        }
        card.setStatus(CardStatusEnum.ACTIVE);
        cardRepository.save(card);
    }

    public void delete(Long id) {
        var card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card with id " + id + " is not found"));
        cardRepository.delete(card);
    }
}
