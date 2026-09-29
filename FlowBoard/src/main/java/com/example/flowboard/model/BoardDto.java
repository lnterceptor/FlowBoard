package com.example.flowboard.model;

public record BoardDto (String boardName, String description) {
    public static BoardDto from(Board board){
        return new BoardDto(board.getBoardName(), board.getDescription());
    }
}
