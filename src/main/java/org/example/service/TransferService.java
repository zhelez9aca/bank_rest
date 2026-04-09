package org.example.service;

import lombok.AllArgsConstructor;
import org.example.enums.CardStatusEnum;
import org.example.enums.TransferStatusEnum;
import org.example.exception.BadTransferRequestException;
import org.example.exception.CardNotFoundException;
import org.example.mapper.TransferMapper;
import org.example.repository.CardRepository;
import org.example.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor

public class TransferService {
    private final TransferRepository transferRepository;
    private final CardRepository cardRepository;
    private final TransferMapper transferMapper;
    @Transactional
    public void transfer(Long fromCardId, Long toCardId, BigDecimal amount){

        if (amount.compareTo(BigDecimal.valueOf(0))<=0 ){
            throw new BadTransferRequestException("Transferring amount must be more than zero");
        }

        var fromCardOpt = cardRepository.findById(fromCardId);
        var toCardOpt = cardRepository.findById(toCardId);
        if (fromCardOpt.isEmpty()|| toCardOpt.isEmpty()){
            throw new CardNotFoundException("Card is not found");
        }
        var fromCard = fromCardOpt.get();
        var toCard = toCardOpt.get();
        var transfer = transferMapper.toTransfer(fromCard, toCard, amount, TransferStatusEnum.PENDING);
        if (!toCard.getUser().equals(fromCard.getUser())) {
            transfer.setStatus(TransferStatusEnum.FAILED);
            transferRepository.save(transfer);
            throw new BadTransferRequestException("You can transfer money only between your cards");
        }
        if (toCard.getStatus() != CardStatusEnum.ACTIVE || fromCard.getStatus() != CardStatusEnum.ACTIVE) {
            transfer.setStatus(TransferStatusEnum.FAILED);
            transferRepository.save(transfer);
            throw  new BadTransferRequestException("Incorrect card status");
        }


        var toBalance = toCard.getBalance();
        var fromBalance = fromCardOpt.get().getBalance();
        if (fromBalance.compareTo(amount)<0){
            transfer.setStatus(TransferStatusEnum.FAILED);
            transferRepository.save(transfer);
            throw  new BadTransferRequestException("Not enough money to transfer");
        }

            //перевод
        toBalance = toBalance.add(amount);
        fromBalance = fromBalance.subtract(amount);
        toCard.setBalance(toBalance);
        fromCard.setBalance(fromBalance);
        cardRepository.save(toCard);
        cardRepository.save(fromCard);
        transfer.setStatus(TransferStatusEnum.SUCCESSFUL);
        transferRepository.save(transfer);

    }
}
