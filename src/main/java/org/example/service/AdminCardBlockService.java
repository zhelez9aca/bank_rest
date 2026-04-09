package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.CardBlockRequestResponseDTO;
import org.example.enums.CardBlockStatusEnum;
import org.example.enums.CardStatusEnum;
import org.example.exception.BadCardBlockRequestException;
import org.example.exception.CardBlockRequestNotFoundException;
import org.example.mapper.CardBlockMapper;
import org.example.model.CardBlockRequest;
import org.example.repository.CardBlockRepository;
import org.example.repository.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminCardBlockService {
    private final CardBlockRepository cardBlockRepository;
    private final CardBlockMapper cardBlockMapper;
    private final CardRepository cardRepository;

    public Page<CardBlockRequestResponseDTO> getCardBlockRequests(int page, int size, CardBlockStatusEnum status){
        var pageable = PageRequest.of(page,size);
        Page<CardBlockRequest> cardBlockRequests;

        if (status == null){
            cardBlockRequests = cardBlockRepository.findAll(pageable);
        }
        else{
            cardBlockRequests = cardBlockRepository.findAllByStatus(status,pageable);
        }
        return cardBlockRequests.map(cardBlockMapper::toResponse);
    }

    public void approve(Long id){
        var request = cardBlockRepository.findById(id)
                .orElseThrow(() -> new CardBlockRequestNotFoundException("Card block request is not found"));
        if (request.getStatus() != CardBlockStatusEnum.PENDING){
            throw new BadCardBlockRequestException("The request has already been processed");
        }
        var card = request.getCard();
        card.setStatus(CardStatusEnum.BLOCKED);
        cardRepository.save(card);
        request.setStatus(CardBlockStatusEnum.APPROVED);
        cardBlockRepository.save(request);
    }

    public void reject(Long id) {
        var request = cardBlockRepository.findById(id)
                .orElseThrow(() -> new CardBlockRequestNotFoundException("Card block request is not found"));
        if (request.getStatus() != CardBlockStatusEnum.PENDING){
            throw new BadCardBlockRequestException("The request has already been processed");
        }
        request.setStatus(CardBlockStatusEnum.REJECTED);
        cardBlockRepository.save(request);
    }
}
