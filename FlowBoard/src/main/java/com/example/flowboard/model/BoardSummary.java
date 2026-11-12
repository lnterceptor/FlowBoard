package com.example.flowboard.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record BoardSummary(String title, String description, Long id, ArrayList<String> collaborators, ArrayList<LabelResponse> labels, Long ownerId) {
    public static BoardSummary from(Board board){
        ArrayList<LabelResponse> labelResponses = new ArrayList<>();
        for(Label label: board.getLabels()){
            labelResponses.add(LabelResponse.from(label));
        }

        return new BoardSummary(board.getBoardName(), board.getDescription(), board.getId(),
                (ArrayList<String>) board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList()),
                labelResponses, board.getUser().getId());
    }
}
