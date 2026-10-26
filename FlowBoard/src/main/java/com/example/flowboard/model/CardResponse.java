package com.example.flowboard.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public record CardResponse(Long id, String title, String description, Long positionInColumn, ArrayList<String> assignees, ArrayList<LabelResponse> labels, LocalDate dueDate, boolean done) {
    public static CardResponse from(Card card){
        ArrayList<LabelResponse> labels = new ArrayList<>();
        for(Label label: card.getLabels()){
            labels.add(LabelResponse.from(label));
        }
        return new CardResponse(card.getId(), card.getTitle(), card.getDescription(), card.getPositionInColumn(),
                (ArrayList<String>) card.getAssignees().stream().map(User::getUsername).collect(Collectors.toList()),
                labels, card.getDueDate(), card.isDone());
    }
}
