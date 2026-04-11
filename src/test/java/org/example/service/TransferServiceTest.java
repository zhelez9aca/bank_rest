package org.example.service;

import org.example.enums.CardStatusEnum;
import org.example.enums.TransferStatusEnum;
import org.example.exception.BadTransferRequestException;
import org.example.mapper.TransferMapper;
import org.example.model.Card;
import org.example.model.Transfer;
import org.example.model.Users;
import org.example.repository.CardRepository;
import org.example.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    CardRepository cardRepository;
    @Mock
    TransferMapper transferMapper;
    @Mock
    TransferRepository transferRepository;
    @InjectMocks
    TransferService transferService;

    @Test
    void transfer_success_updatesBalances_andSaves() {
        Users user = new Users();
        user.setId(1L);

        Card from = new Card();
        from.setId(1L);
        from.setUser(user);
        from.setStatus(CardStatusEnum.ACTIVE);
        from.setBalance(new BigDecimal("100.00"));
        Transfer transfer = new Transfer();

        Card to = new Card();
        to.setId(2L);
        to.setUser(user);
        to.setStatus(CardStatusEnum.ACTIVE);
        to.setBalance(new BigDecimal("10.00"));


        when(cardRepository.findById(1L)).thenReturn(Optional.of(from));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(to));
        when(transferMapper.toTransfer(from, to, new BigDecimal("30.00"), TransferStatusEnum.PENDING))
                .thenReturn(transfer);

        transferService.transfer(1L, 2L, new BigDecimal("30.00"));

        verify(cardRepository).save(from);
        verify(cardRepository).save(to);
        verify(transferRepository, atLeastOnce()).save(any(Transfer.class));
    }
    @Test
    void transfer_failure(){
        Users user = new Users();
        user.setId(1L);

        Card from = new Card();
        from.setId(1L);
        from.setUser(user);
        from.setStatus(CardStatusEnum.ACTIVE);
        from.setBalance(new BigDecimal("10.00"));
        Transfer transfer = new Transfer();

        Card to = new Card();
        to.setId(2L);
        to.setUser(user);
        to.setStatus(CardStatusEnum.ACTIVE);
        to.setBalance(new BigDecimal("10.00"));

        when(cardRepository.findById(1L)).thenReturn(Optional.of(from));
        when(cardRepository.findById(2L)).thenReturn(Optional.of(to));
        when(transferMapper.toTransfer(from, to, new BigDecimal("30.00"), TransferStatusEnum.PENDING))
                .thenReturn(transfer);


        assertThrows(BadTransferRequestException.class,
                () -> transferService.transfer(1L, 2L, new BigDecimal("30.00")));

    }

    }
