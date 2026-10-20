package com.example.flowboard.controller;

import com.example.flowboard.model.CardResponse;
import com.example.flowboard.model.ChangeObjectOrder;
import com.example.flowboard.model.ChangeColumnOfCard;
import com.example.flowboard.model.CreateCardRequest;
import com.example.flowboard.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cards")
public class CardController {
    CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping("/")
    public ResponseEntity<CardResponse> createCard(@RequestBody CreateCardRequest createCardRequest){
        return cardService.createCard(createCardRequest);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardResponse> updateCard(@RequestBody CreateCardRequest newCard, @PathVariable Long cardId){
        return cardService.updateCard(cardId, newCard);
    }

    @PatchMapping("/reorder")
    public ResponseEntity<?> changeOrderInColumn(@RequestBody ChangeObjectOrder changeObjectOrder){
        System.out.println("order of cards in column changed");
        return cardService.changeOrderInColumn(changeObjectOrder);
    }

    @PatchMapping("/moveCard")
    public ResponseEntity<?> changeColumnAndPlacementOfCard(@RequestBody ChangeColumnOfCard changeColumnOfCard){
        System.out.println("moved card to different column");
        return cardService.moveCardToDifferentColumn(changeColumnOfCard);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId){
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<?> getCards(@PathVariable Long columnId){
        return cardService.getCardsFromColumn(columnId);
    }
}
