package com.example.flowboard.controller;

import com.example.flowboard.model.BoardResponse;
import com.example.flowboard.model.CreateBoardRequest;
import com.example.flowboard.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @PostMapping("/")
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody CreateBoardRequest boardRequest) {
        return boardService.createBoard(boardRequest);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ArrayList<BoardResponse>> getUserBoards(@Valid @PathVariable Long userId){
        return boardService.getUserBoards(userId);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }
}
