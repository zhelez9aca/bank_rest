package org.example.service;

import org.example.dto.CardResponseDTO;
import org.example.enums.CardStatusEnum;
import org.example.exception.InvalidTokenException;
import org.example.mapper.CardMapper;
import org.example.model.Card;
import org.example.model.Users;
import org.example.repository.CardRepository;
import org.example.repository.UserRepository;
import org.example.security.JWToken;
import org.example.util.EncryptionUtil;
import org.example.util.PanGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock CardRepository cardRepository;
    @Mock UserRepository userRepository;
    @Mock JWToken jwToken;
    @Mock CardMapper cardMapper;
    @Mock EncryptionUtil encryptionUtil;
    @Mock PanGenerator panGenerator;

    @InjectMocks CardService cardService;

    @Test
    void createCard_success_savesCard() {
        Users user = new Users();
        user.setId(1L);

        when(jwToken.isValid("token")).thenReturn(true);
        when(jwToken.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(panGenerator.randomCardNumber()).thenReturn("1234567890123456");
        when(encryptionUtil.encrypt("1234567890123456")).thenReturn("encrypted");
        when(cardMapper.toResponse(any(Card.class)))
                .thenReturn(new CardResponseDTO("**** **** **** 3456", "John Doe", 4, 2030, CardStatusEnum.ACTIVE, BigDecimal.ZERO));

        cardService.createCard("token", "John Doe");

        ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(captor.capture());
        Card saved = captor.getValue();

        assertEquals("encrypted", saved.getEncryptedPan());
        assertEquals("3456", saved.getLast4());
        assertEquals("John Doe", saved.getHolderName());
        assertEquals(CardStatusEnum.ACTIVE, saved.getStatus());
    }

    @Test
    void createCard_invalidToken_throws() {
        when(jwToken.isValid("bad")).thenReturn(false);

        assertThrows(InvalidTokenException.class,
                () -> cardService.createCard("bad", "John Doe"));

        verifyNoInteractions(userRepository);
        verifyNoInteractions(cardRepository);
    }
}
