package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public record ColumnResponse (
        Long id,
        String title,
        Long positionInBoard,
        List<CardResponse> cards
) {
    public static ColumnResponse from(BoardColumn column){
        List<CardResponse> cardResponses = new ArrayList<>();
        for(Card card: column.getCards()){
            cardResponses.add(CardResponse.from(card));
        }
        return new ColumnResponse(column.getId(), column.getTitle(), column.getPositionInBoard(), cardResponses);
    }
}
