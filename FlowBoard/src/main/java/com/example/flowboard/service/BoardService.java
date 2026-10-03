package com.example.flowboard.service;

import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.Board;
import com.example.flowboard.model.BoardResponse;
import com.example.flowboard.model.CreateBoardRequest;
import com.example.flowboard.model.User;
import com.example.flowboard.repository.BoardRepository;
import com.example.flowboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<BoardResponse> createBoard(CreateBoardRequest boardRequest){
        Board board = new Board();
        board.setUser(userRepository.findById(boardRequest.userId()).orElseThrow(() -> new ResourceNotFoundException("User does not exist")));
        board.setDescription(boardRequest.desc());
        board.setBoardName(boardRequest.name());
        boardRepository.save(board);

        return ResponseEntity.status(HttpStatus.CREATED).body(BoardResponse.from(board));
    }

    public ResponseEntity<ArrayList<BoardResponse>> getUserBoards(Long userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        ArrayList<BoardResponse> boards = new ArrayList<>();
        for(Board board: user.getBoards()){
            boards.add(BoardResponse.from(board));
        }
        return ResponseEntity.ok(boards);
    }

    public ResponseEntity<?> deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
        return ResponseEntity.noContent().build();
    }

}
