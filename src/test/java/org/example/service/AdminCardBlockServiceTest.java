package org.example.service;

import org.example.enums.CardBlockStatusEnum;
import org.example.enums.CardStatusEnum;
import org.example.exception.BadCardBlockRequestException;
import org.example.mapper.CardBlockMapper;
import org.example.model.Card;
import org.example.model.CardBlockRequest;
import org.example.repository.CardBlockRepository;
import org.example.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCardBlockServiceTest {

    @Mock CardBlockRepository cardBlockRepository;
    @Mock CardRepository cardRepository;
    @Mock CardBlockMapper cardBlockMapper;

    @InjectMocks AdminCardBlockService service;

    @Test
    void approve_setsStatusAndBlocksCard() {
        Card card = new Card();
        card.setStatus(CardStatusEnum.ACTIVE);

        CardBlockRequest request = new CardBlockRequest();
        request.setStatus(CardBlockStatusEnum.PENDING);
        request.setCard(card);

        when(cardBlockRepository.findById(1L)).thenReturn(Optional.of(request));

        service.approve(1L);

        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(cardCaptor.capture());
        assertEquals(CardStatusEnum.BLOCKED, cardCaptor.getValue().getStatus());

        ArgumentCaptor<CardBlockRequest> reqCaptor = ArgumentCaptor.forClass(CardBlockRequest.class);
        verify(cardBlockRepository, atLeastOnce()).save(reqCaptor.capture());
        assertEquals(CardBlockStatusEnum.APPROVED, reqCaptor.getValue().getStatus());
    }

    @Test
    void reject_alreadyProcessed_throws() {
        CardBlockRequest request = new CardBlockRequest();
        request.setStatus(CardBlockStatusEnum.APPROVED);

        when(cardBlockRepository.findById(1L)).thenReturn(Optional.of(request));

        assertThrows(BadCardBlockRequestException.class, () -> service.reject(1L));
        verify(cardBlockRepository, never()).save(request);
    }
}
