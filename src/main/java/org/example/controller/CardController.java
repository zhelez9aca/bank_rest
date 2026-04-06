package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.CardResponse;
import org.example.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardController {
    private  final CardService CardService;
    @GetMapping
    public ResponseEntity<List<CardResponse>> getCards(@CookieValue(value ="access_token",required=false) String cookieToken,
                                                       @RequestHeader(value = "Authorization",required = false) String headerToken){

        List<CardResponse> response;
        if (headerToken!=null&& headerToken.startsWith("Bearer ")){
            response = CardService.getCards(headerToken.substring(7));
            return ResponseEntity.ok(response);
        }
        response = CardService.getCards(cookieToken);
        return  ResponseEntity.ok(response);


    }

}
