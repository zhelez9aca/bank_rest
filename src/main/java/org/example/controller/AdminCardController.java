package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.CardResponseDTO;
import org.example.dto.DepositRequestDTO;
import org.example.enums.CardStatusEnum;
import org.example.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCardController {
    private final CardService cardService;

    @PostMapping("/cards/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestBody DepositRequestDTO request){
        cardService.deposit(id, request.amount());
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/cards")
    public ResponseEntity<Page<CardResponseDTO>> getCards(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size,
                                                          @RequestParam(required = false) CardStatusEnum cardStatus){
        return ResponseEntity.ok(cardService.getAllCards(page, size, cardStatus));
    }

    @PatchMapping("/cards/{id}/block")
    public ResponseEntity<Void> block(@PathVariable Long id){
        cardService.block(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/cards/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id){
        cardService.activate(id);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cardService.delete(id);
        return ResponseEntity.status(200).build();
    }
}
