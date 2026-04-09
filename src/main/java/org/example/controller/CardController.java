package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.CardBlockRequestDTO;
import org.example.dto.CardResponseDTO;
import org.example.dto.CreateCardRequestDTO;
import org.example.enums.CardStatusEnum;
import org.example.service.CardService;
import org.example.service.UserCardBlockRequestService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;
    private final UserCardBlockRequestService blockService;

    @GetMapping
    public ResponseEntity<Page<CardResponseDTO>> getCards(@CookieValue(value = "access_token", required = false) String cookieToken,
                                                          @RequestHeader(value = "Authorization", required = false) String headerToken,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size,
                                                          @RequestParam(required = false) CardStatusEnum statusEnum) {
        Page<CardResponseDTO> response;
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            response = cardService.getCards(headerToken.substring(7), page, size, statusEnum);
            return ResponseEntity.ok(response);
        }
        response = cardService.getCards(cookieToken, page, size, statusEnum);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@CookieValue(value = "access_token", required = false) String cookieToken,
                                                      @RequestHeader(value = "Authorization", required = false) String headerToken,
                                                      @RequestBody CreateCardRequestDTO request) {
        var holderName = request.holderName();
        CardResponseDTO response;
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            response = cardService.createCard(headerToken.substring(7), holderName);
            return ResponseEntity.ok(response);
        }
        response = cardService.createCard(cookieToken, holderName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/block-request")
    public ResponseEntity<Void> blockRequest(@PathVariable Long id,
                                             @CookieValue(value = "access_token", required = false) String cookieToken,
                                             @RequestHeader(value = "Authorization", required = false) String headerToken,
                                             @RequestBody CardBlockRequestDTO request) {
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            blockService.blockRequest(id, headerToken.substring(7), request.reason());
            return ResponseEntity.status(200).build();
        }
        blockService.blockRequest(id, cookieToken, request.reason());
        return ResponseEntity.status(200).build();
    }
}
