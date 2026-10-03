package com.example.flowboard.service;

import com.example.flowboard.exception.InvalidMoveCardException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.CardRepository;
import com.example.flowboard.repository.ColumnRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    CardRepository cardRepository;
    ColumnRepository columnRepository;

    public CardService(CardRepository cardRepository, ColumnRepository columnRepository){
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
    }

    @Transactional
    public ResponseEntity<?> moveCardToDifferentColumn(ChangeColumnOfCard newPlacement){
        Card card = cardRepository.findCardById(newPlacement.cardId()).orElseThrow(() -> new ResourceNotFoundException("Card with id: " + newPlacement.cardId() + " not found"));
        BoardColumn column = card.getColumn();

        for(Card tempCard: column.getCards()){
            if(tempCard.getPositionInColumn() > card.getPositionInColumn()){
                tempCard.setPositionInColumn(tempCard.getPositionInColumn() - 1);
                tempCard.setLastUpdateDate(Instant.now());
            }
        }

        column.removeCard(card);
        column.setLastUpdateDate(Instant.now());

        BoardColumn newColumn = columnRepository.findById(newPlacement.newColumnId()).orElseThrow(() -> new ResourceNotFoundException("Target column does not exist"));

        if(!newColumn.getBoard().equals(column.getBoard())){
            throw new InvalidMoveCardException("Cannot move card to a different board", "INVALID_CARD_MOVE");
        }
        Long newPosition = newPlacement.placementInNewColumn();
        if(newPosition >= newColumn.getCards().size() || newPosition < 0) {
            if (newPosition < 0) {
                newPosition = 0L;
            } else {
                newPosition = (long) newColumn.getCards().size();
            }
        }

        for(Card tempCard: newColumn.getCards()){
            if(tempCard.getPositionInColumn() >= newPosition){
                tempCard.setPositionInColumn(tempCard.getPositionInColumn() + 1);
                tempCard.setLastUpdateDate(Instant.now());
            }
        }
        card.setPositionInColumn(newPosition);
        newColumn.addCard(card);
        columnRepository.save(newColumn);
        cardRepository.save(card);
        return ResponseEntity.ok(CardResponse.from(card));
    }

    @Transactional
    public ResponseEntity<?> createCard(CreateCardRequest createCardRequest){
        Card card = new Card();
        BoardColumn column = columnRepository.findBoardColumnsById(createCardRequest.columnId()).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));

        card.setDescription(createCardRequest.description());
        card.setPositionInColumn((long) column.getCards().size());
        card.setDueDate(createCardRequest.dueDate());
        card.setCreationDate(Instant.now());

        column.setLastUpdateDate(Instant.now());
        column.addCard(card);
        cardRepository.save(card);
        columnRepository.save(column);

        return ResponseEntity.ok(CardResponse.from(card));
    }

    @Transactional
    public ResponseEntity<List<CardResponse>> changeOrderInColumn(ChangeObjectOrder changeObjectOrder){
        Card card = cardRepository.findCardById(changeObjectOrder.idOfFirstObject()).orElseThrow(() -> new ResourceNotFoundException("Card does not exist"));
        BoardColumn column = card.getColumn();

        Long newPosition = changeObjectOrder.positionOfSecondObject();
        if(newPosition >= column.getCards().size() || newPosition < 0) {
            if (newPosition < 0) {
                newPosition = 0L;
            } else {
                newPosition = (long) column.getCards().size() - 1;
            }
        }

        for(Card tempCard: column.getCards()){
            if(tempCard.getPositionInColumn().equals(newPosition) && !card.equals(tempCard)){
                tempCard.setPositionInColumn(card.getPositionInColumn());
                tempCard.setLastUpdateDate(Instant.now());

                card.setPositionInColumn(newPosition);
                card.setLastUpdateDate(Instant.now());

                column.setLastUpdateDate(Instant.now());
                break;
            }
        }
        ArrayList<CardResponse> cardResponses = new ArrayList<>();
        for (Card card1 : column.getCards()){
            cardResponses.add(CardResponse.from(card1));
        }
        return ResponseEntity.ok(cardResponses);
    }
    @Transactional
    public ResponseEntity<?> renameCard(Long cardId, String newName){
        Card card = cardRepository.findCardById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card does not exist"));
        card.setDescription(newName);
        card.setLastUpdateDate(Instant.now());
        return ResponseEntity.ok(card);
    }

    @Transactional
    public ResponseEntity<?> updateDueDate(Long cardId, LocalDate newDate){
        Card card = cardRepository.findCardById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card does not exist"));
        card.setDueDate(newDate);
        card.setLastUpdateDate(Instant.now());
        return ResponseEntity.ok(card);
    }

    @Transactional
    public ResponseEntity<?> deleteCard(Long cardId){
        Card card = cardRepository.findCardById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card does not exist"));

        BoardColumn column = card.getColumn();
        long positionOfCardInColumn = card.getPositionInColumn();

        column.getCards().remove((int) positionOfCardInColumn);

        for(Card tempCard: column.getCards()){
            if(tempCard.getPositionInColumn() > card.getPositionInColumn()){
              tempCard.setPositionInColumn(tempCard.getPositionInColumn() - 1);
              tempCard.setLastUpdateDate(Instant.now());
            }
        }
        column.setLastUpdateDate(Instant.now());
        cardRepository.delete(card);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getCardsFromColumn(Long columnId){
        BoardColumn column = columnRepository.findBoardColumnsById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));
        ArrayList<CardResponse> cards = new ArrayList<>();
        for(Card card: column.getCards()){
            cards.add(CardResponse.from(card));
        }
        return ResponseEntity.ok(cards);
    }
}
