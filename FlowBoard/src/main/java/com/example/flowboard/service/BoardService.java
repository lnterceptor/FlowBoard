package com.example.flowboard.service;

import com.example.flowboard.model.Board;
import com.example.flowboard.model.BoardDto;
import com.example.flowboard.model.CreateBoardRequest;
import com.example.flowboard.repository.BoardRepository;
import com.example.flowboard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class BoardService {
    BoardRepository boardRepository;
    UserRepository userRepository;
    public BoardService(BoardRepository boardRepository, UserRepository userRepository){
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }


    public ResponseEntity<BoardDto> createBoard(CreateBoardRequest boardRequest){
        Board board = new Board();
        board.setUser(userRepository.findById(boardRequest.userId()).orElseThrow());//todo: throw actual error
        board.setDescription(boardRequest.desc());
        board.setBoardName(boardRequest.name());
        boardRepository.save(board);

        return ResponseEntity.ok(BoardDto.from(board));
    }

    public ResponseEntity<ArrayList<BoardDto>> getUserBoards(Long userId){
        ArrayList<BoardDto> boards = (ArrayList<BoardDto>) boardRepository.findAllByUserId(userId)
                .stream().map(BoardDto::from).collect(Collectors.toList());
        return ResponseEntity.ok(boards);
    }

}
