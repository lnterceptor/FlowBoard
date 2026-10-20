package com.example.flowboard.model;

import java.util.ArrayList;

public record BoardSummary(String title, String description, Long id) {
    public static BoardSummary from(Board board){
        return new BoardSummary(board.getBoardName(), board.getDescription(), board.getId());
    }
}
