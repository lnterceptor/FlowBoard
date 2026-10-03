package com.example.flowboard.model;

public record CardResponse(Long id, String description, Long positionInColumn) {
    public static CardResponse from(Card card){
        return new CardResponse(card.id, card.description, card.positionInColumn);
    }
}
