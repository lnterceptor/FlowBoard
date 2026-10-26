package com.example.flowboard.service;

import com.example.flowboard.exception.InvalidMoveCardException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.CardRepository;
import com.example.flowboard.repository.ColumnRepository;
import com.example.flowboard.repository.LabelRepository;
import com.example.flowboard.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    @PersistenceContext
    private EntityManager entityManager;

    CardRepository cardRepository;
    ColumnRepository columnRepository;

    public CardService(CardRepository cardRepository, ColumnRepository columnRepository, UserRepository userRepository, LabelRepository labelRepository){
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
        this.userRepository = userRepository;
        this.labelRepository = labelRepository;
    }

    @Transactional
    public ResponseEntity<?> moveCardToDifferentColumn(ChangeColumnOfCard newPlacement){
        Card card = cardRepository.findCardById(newPlacement.cardId()).orElseThrow(() -> new ResourceNotFoundException("Card with id: " + newPlacement.cardId() + " not found"));
        BoardColumn column = columnRepository.findBoardColumnsById(card.getColumn().getId()).orElseThrow();
        BoardColumn newColumn = columnRepository.findBoardColumnsById(newPlacement.newColumnId()).orElseThrow(() -> new ResourceNotFoundException("Target column does not exist"));

        if(!newColumn.getBoard().equals(column.getBoard())){
            throw new InvalidMoveCardException("Cannot move card to a different board", "INVALID_CARD_MOVE");
        }

        column.removeCard(card);

        for(Card tempCard: column.getCards()){
            if(tempCard.getPositionInColumn() > card.getPositionInColumn()){
                tempCard.setPositionInColumn(tempCard.getPositionInColumn() - 1);
                tempCard.setLastUpdateDate(Instant.now());
            }
        }

        column.setLastUpdateDate(Instant.now());
        Long newPosition = newPlacement.placementInNewColumn();
        if(newPosition > newColumn.getCards().size() || newPosition < 0) {
            if (newPosition <= 0) {
                newPosition = 0L;
            } else {
                newPosition = (long) newColumn.getCards().size();
            }
        }

        card.setPositionInColumn(newPosition);

        for(Card tempCard: newColumn.getCards()){
            if(tempCard.getPositionInColumn() >= card.getPositionInColumn()){
                tempCard.setPositionInColumn(tempCard.getPositionInColumn() + 1);
                tempCard.setLastUpdateDate(Instant.now());
            }
        }

        newColumn.addCard(card);
        entityManager.flush();

        ArrayList<ColumnResponse> columnResponses = new ArrayList<>();
        for (BoardColumn tempColumn : newColumn.getBoard().getColumns()){
            columnResponses.add(ColumnResponse.from(tempColumn));
        }
        return ResponseEntity.ok(columnResponses);
    }

    @Transactional
    public ResponseEntity<CardResponse> createCard(CreateCardRequest createCardRequest){
        Card card = new Card();
        BoardColumn column = columnRepository.findBoardColumnsById(createCardRequest.columnId()).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));

        card.setTitle(createCardRequest.title());
        card.setDescription(createCardRequest.description());
        card.setPositionInColumn((long) column.getCards().size());
        card.setDueDate(createCardRequest.dueDate());
        card.setCreationDate(Instant.now());
        card.setDone(createCardRequest.done());

        if(createCardRequest.assignees() != null) {
            for (String assignee : createCardRequest.assignees()) {
                User user = userRepository.findUserByUsername(assignee).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
                card.addAssignee(user);
            }
        }
        if(createCardRequest.labels() != null) {
            for (LabelRequest label : createCardRequest.labels()) {
                Label newLabel = labelRepository.findLabelByNameAndBoardId(label.name(), column.getBoard().getId()).orElseThrow(() -> new ResourceNotFoundException("Label does not exist"));
                card.addLabel(newLabel);
            }
        }

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
            if (newPosition <= 0) {
                newPosition = 0L;
            } else {
                newPosition = (long) column.getCards().size() - 1;
            }
        }

        for(Card tempCard: column.getCards()){
            if(card.getPositionInColumn() > newPosition && tempCard.getPositionInColumn() >= newPosition
                    && tempCard.getPositionInColumn() < card.getPositionInColumn()){

                tempCard.setPositionInColumn(tempCard.getPositionInColumn() + 1);
                tempCard.setLastUpdateDate(Instant.now());
            }
            else if(card.getPositionInColumn() < newPosition && tempCard.getPositionInColumn() > card.getPositionInColumn()
                    && tempCard.getPositionInColumn() <= newPosition){
                tempCard.setPositionInColumn(tempCard.getPositionInColumn() - 1);
                tempCard.setLastUpdateDate(Instant.now());
            }
        }
        card.setPositionInColumn(newPosition);
        card.setLastUpdateDate(Instant.now());
        ArrayList<CardResponse> cardResponses = new ArrayList<>();
        for (Card card1 : column.getCards()){
            cardResponses.add(CardResponse.from(card1));
        }
        return ResponseEntity.ok(cardResponses);
    }

    @Transactional
    public ResponseEntity<CardResponse> updateCard(Long cardId, CreateCardRequest newCard){
        System.out.println(newCard.labels());
        Card card = cardRepository.findCardById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card does not exist"));

        card.setTitle(newCard.title());
        card.setDescription(newCard.description());
        card.setDueDate(newCard.dueDate());
        card.setLastUpdateDate(Instant.now());
        card.setDone(newCard.done());

        card.getAssignees().removeIf(user -> !newCard.assignees().contains(user.getUsername()));


        HashSet<String> currentAssignees = (HashSet<String>) card.getAssignees().stream().map(User::getUsername).collect(Collectors.toSet());
        if(newCard.assignees() != null) {
            for (String assignee : newCard.assignees()) {
                if (!currentAssignees.contains(assignee)) {
                    User user = userRepository.findUserByUsername(assignee).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
                    card.addAssignee(user);
                }
            }
        }


        for(Label label: new ArrayList<>(card.getLabels())){
            if (!newCard.labels().contains(new LabelRequest(label.getName(), label.getColor()))) {
                card.removeLabel(label);
            }
        }

        HashSet<String> currentLabels = (HashSet<String>) card.getLabels().stream().map(Label::getName).collect(Collectors.toSet());
        if(newCard.labels() != null) {
            for (LabelRequest label : newCard.labels()) {
                if (!currentLabels.contains(label.name())) {
                    Label newLabel = labelRepository.findLabelByNameAndBoardId(label.name(), card.getColumn().getBoard().getId()).orElseThrow(() -> new ResourceNotFoundException("Label does not exist"));
                    card.addLabel(newLabel);
                }
            }
        }

        return ResponseEntity.ok(CardResponse.from(card));
    }

    @Transactional
    public ResponseEntity<?> deleteCard(Long cardId){
        Card card = cardRepository.findCardById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card does not exist"));

        BoardColumn column = card.getColumn();
        column.removeCard(card);

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
