package com.example.flowboard.controller;

import com.example.flowboard.model.CardResponse;
import com.example.flowboard.model.ChangeObjectOrder;
import com.example.flowboard.model.ChangeColumnOfCard;
import com.example.flowboard.model.CreateCardRequest;
import com.example.flowboard.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
@RestController
@RequestMapping("/api/cards")
public class CardController {
    CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping("/")
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CreateCardRequest createCardRequest){
        return cardService.createCard(createCardRequest);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardResponse> updateCard(@Valid @RequestBody CreateCardRequest newCard, @Positive @PathVariable Long cardId){
        return cardService.updateCard(cardId, newCard);
    }

    @PatchMapping("/reorder")
    public ResponseEntity<?> changeOrderInColumn(@Valid @RequestBody ChangeObjectOrder changeObjectOrder){
        return cardService.changeOrderInColumn(changeObjectOrder);
    }

    @PatchMapping("/moveCard")
    public ResponseEntity<?> changeColumnAndPlacementOfCard(@Valid @RequestBody ChangeColumnOfCard changeColumnOfCard){
        return cardService.moveCardToDifferentColumn(changeColumnOfCard);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@Positive @PathVariable Long cardId){
        return cardService.deleteCard(cardId);
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<?> getCards(@Positive @PathVariable Long columnId){
        return cardService.getCardsFromColumn(columnId);
    }
}
