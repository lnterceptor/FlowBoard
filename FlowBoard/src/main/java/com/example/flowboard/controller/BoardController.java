package com.example.flowboard.controller;

import com.example.flowboard.model.BoardResponse;
import com.example.flowboard.model.BoardSummary;
import com.example.flowboard.model.CreateBoardRequest;
import com.example.flowboard.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @PostMapping("/")
    public ResponseEntity<BoardSummary> createBoard(@Valid @RequestBody CreateBoardRequest boardRequest) {
        return boardService.createBoard(boardRequest);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ArrayList<BoardSummary>> getUserBoards(@Valid @PathVariable Long userId){
        return boardService.getUserBoards(userId);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoardWithGivenId(@PathVariable Long boardId){
        return boardService.getBoard(boardId);
    }
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardSummary> updateBoard(@PathVariable Long boardId, @RequestBody CreateBoardRequest boardRequest){
        return boardService.updateBoard(boardId, boardRequest);
    }
}
