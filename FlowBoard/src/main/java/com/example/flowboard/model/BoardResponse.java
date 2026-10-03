package com.example.flowboard.model;

import java.util.ArrayList;
import java.util.List;

public record BoardResponse(String boardName, String description, Long id, List<ColumnResponse> columns) {
    public static BoardResponse from(Board board){
        ArrayList<ColumnResponse> columnResponses = new ArrayList<>();
        for(BoardColumn column: board.getColumns()) {
            columnResponses.add(ColumnResponse.from(column));
        }
        return new BoardResponse(board.getBoardName(), board.getDescription(), board.getId(), columnResponses);
    }
}
