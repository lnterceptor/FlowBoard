package com.example.flowboard.controller;

import com.example.flowboard.model.CollaboratorRequest;
import com.example.flowboard.model.BoardSummary;
import com.example.flowboard.model.CreateBoardRequest;
import com.example.flowboard.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
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
    public ResponseEntity<ArrayList<BoardSummary>> getUserBoards(@Positive @PathVariable Long userId){
        return boardService.getUserBoards(userId);
    }

    @GetMapping("/{userId}/getJoinedBoards")
    public ResponseEntity<ArrayList<BoardSummary>> getUserJoinedBoards(@Positive @PathVariable Long userId){
        return boardService.getUserJoinedBoards(userId);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@Positive @PathVariable Long boardId, @Positive @RequestParam Long userId){
        return boardService.deleteBoard(boardId, userId);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoardWithGivenId(@Positive @PathVariable Long boardId){
        return boardService.getBoard(boardId);
    }
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardSummary> updateBoard(@Positive @PathVariable Long boardId,@Valid @RequestBody CreateBoardRequest boardRequest){
        return boardService.updateBoard(boardId, boardRequest);
    }
    @PostMapping("/addCollaborator")
    public ResponseEntity<ArrayList<String>> addCollaboratorToBoard(@Valid @RequestBody CollaboratorRequest collaboratorRequest){
        return boardService.addCollaborator(collaboratorRequest);
    }

    @DeleteMapping("/removeCollabolator")
    public ResponseEntity<?> removeCollaborator(@Valid @RequestBody CollaboratorRequest collaboratorRequest, @RequestParam Long userAskingForMemberDeleteId){
        return boardService.removeCollaborator(collaboratorRequest, userAskingForMemberDeleteId);
    }
}
