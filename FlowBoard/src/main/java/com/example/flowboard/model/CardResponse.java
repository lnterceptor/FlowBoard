package com.example.flowboard.model;

public record CardResponse(Long id, String title, String description, Long positionInColumn) {
    public static CardResponse from(Card card){
        return new CardResponse(card.getId(), card.getTitle(), card.getDescription(), card.getPositionInColumn());
    }
}
