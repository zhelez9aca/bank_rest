package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.TransferRequest;
import org.example.model.Transfer;
import org.example.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/transfers")
public class TransferController {
    private  final TransferService transferService;
    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request){
        transferService.transfer(request.fromCardId(), request.toCardId(), request.amount());
        return ResponseEntity.status(200).build();

    }
}
