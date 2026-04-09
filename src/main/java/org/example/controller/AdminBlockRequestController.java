package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.CardBlockRequestResponseDTO;
import org.example.enums.CardBlockStatusEnum;
import org.example.service.AdminCardBlockService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/block-requests")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminBlockRequestController {
    private final AdminCardBlockService cardBlockService;

    @GetMapping
    public ResponseEntity<Page<CardBlockRequestResponseDTO>> getCardBlockRequests(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "20") int size,
                                                                                  @RequestParam(required = false) CardBlockStatusEnum status){
        return ResponseEntity.ok(cardBlockService.getCardBlockRequests(page, size, status));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long id){
        cardBlockService.approve(id);
        return ResponseEntity.status(200).build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id){
        cardBlockService.reject(id);
        return ResponseEntity.status(200).build();
    }
}
