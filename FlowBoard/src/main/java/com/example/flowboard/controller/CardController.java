package com.example.flowboard.controller;

import com.example.flowboard.model.ChangeObjectOrder;
import com.example.flowboard.model.ChangeColumnOfCard;
import com.example.flowboard.model.CreateCardRequest;
import com.example.flowboard.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createCard(@RequestBody CreateCardRequest createCardRequest){
        return cardService.createCard(createCardRequest);
    }

    @PatchMapping("updateName/{cardId}")
    public ResponseEntity<?> renameCard(@RequestBody String newName, @PathVariable Long cardId){
        return cardService.renameCard(cardId, newName);
    }

    @PatchMapping("updateDueDate/{cardId}")
    public ResponseEntity<?> updateDueDate(@RequestBody LocalDate newName, @PathVariable Long cardId){
        return cardService.updateDueDate(cardId, newName);
    }

    @PatchMapping("/reorder")
    public ResponseEntity<?> changeOrderInColumn(@RequestBody ChangeObjectOrder changeObjectOrder){
        return cardService.changeOrderInColumn(changeObjectOrder);
    }

    @PatchMapping("/moveCard")
    public ResponseEntity<?> changeColumnAndPlacementOfCard(@RequestBody ChangeColumnOfCard changeColumnOfCard){
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
