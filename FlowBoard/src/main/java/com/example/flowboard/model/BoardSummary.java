package com.example.flowboard.model;

import java.util.Set;
import java.util.stream.Collectors;

public record BoardSummary(String title, String description, Long id, Set<String> collaborators) {
    public static BoardSummary from(Board board){
        return new BoardSummary(board.getBoardName(), board.getDescription(), board.getId(),
                board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toSet()));
    }
}
