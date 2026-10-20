package com.example.flowboard.service;

import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.BoardRepository;
import com.example.flowboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class BoardService {
    BoardRepository boardRepository;
    UserRepository userRepository;
    public BoardService(BoardRepository boardRepository, UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<BoardSummary> createBoard(CreateBoardRequest boardRequest){
        Board board = new Board();
        board.setUser(userRepository.findById(boardRequest.userId()).orElseThrow(() -> new ResourceNotFoundException("User does not exist")));
        board.setDescription(boardRequest.desc());
        board.setBoardName(boardRequest.name());
        boardRepository.save(board);

        return ResponseEntity.status(HttpStatus.CREATED).body(BoardSummary.from(board));
    }

    public ResponseEntity<ArrayList<BoardSummary>> getUserBoards(Long userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        ArrayList<BoardSummary> boards = new ArrayList<>();
        for(Board board: user.getBoards()){
            boards.add(BoardSummary.from(board));
        }
        return ResponseEntity.ok(boards);
    }

    public ResponseEntity<?> deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<BoardSummary> getBoard(Long boardId){
        Board board = boardRepository.findBoardById(boardId).orElseThrow();
        return ResponseEntity.ok(BoardSummary.from(board));
    }

    @Transactional
    public ResponseEntity<BoardSummary> updateBoard(Long boardId, CreateBoardRequest updatedBoard){
        Board board = boardRepository.findBoardById(boardId).orElseThrow();
        board.setBoardName(updatedBoard.name());
        board.setDescription(updatedBoard.desc());
        board.setLastUpdateDate(Instant.now());
        return ResponseEntity.ok(BoardSummary.from(board));
    }
}
