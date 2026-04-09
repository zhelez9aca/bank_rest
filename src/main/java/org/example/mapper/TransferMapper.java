package org.example.mapper;

import org.example.enums.TransferStatusEnum;
import org.example.model.Card;
import org.example.model.Transfer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferMapper {
    public Transfer toTransfer(Card fromCard, Card toCard, BigDecimal amount, TransferStatusEnum status){
        var transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setStatus(status);
        transfer.setUser(fromCard.getUser());
        transfer.setFromCard(fromCard);
        transfer.setToCard(toCard);
        return transfer;
    }
}
