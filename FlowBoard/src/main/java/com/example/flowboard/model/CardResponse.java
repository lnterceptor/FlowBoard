package com.example.flowboard.model;

import java.util.Set;
import java.util.stream.Collectors;

public record CardResponse(Long id, String title, String description, Long positionInColumn, Set<String> assignees) {
    public static CardResponse from(Card card){
        return new CardResponse(card.getId(), card.getTitle(), card.getDescription(), card.getPositionInColumn(),
                card.getAssignees().stream().map(User::getUsername).collect(Collectors.toSet()));
    }
}
