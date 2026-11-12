package com.example.flowboard.service;

import com.example.flowboard.exception.AccessDeniedException;
import com.example.flowboard.exception.ResourceAlreadyExistsException;
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
import java.util.Objects;
import java.util.stream.Collectors;

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
        User user = userRepository.findById(boardRequest.userId()).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        board.setUser(user);
        board.setDescription(boardRequest.desc());
        board.setBoardName(boardRequest.name());
        board.addCollaborator(user);
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

    public ResponseEntity<?> deleteBoard(Long boardId, Long ownerId){
        Board board = boardRepository.findBoardById(boardId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified board."));
        if(!Objects.equals(board.getUser().getId(), ownerId)){
            throw new AccessDeniedException("Cannot delete board that does not belong to user");
        }
        boardRepository.deleteById(boardId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<BoardSummary> getBoard(Long boardId){
        Board board = boardRepository.findBoardById(boardId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified board."));
        return ResponseEntity.ok(BoardSummary.from(board));
    }

    @Transactional
    public ResponseEntity<BoardSummary> updateBoard(Long boardId, CreateBoardRequest updatedBoard){
        Board board = boardRepository.findBoardById(boardId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified board."));
        board.setBoardName(updatedBoard.name());
        board.setDescription(updatedBoard.desc());
        board.setLastUpdateDate(Instant.now());
        return ResponseEntity.ok(BoardSummary.from(board));
    }

    public ResponseEntity<ArrayList<BoardSummary>> getUserJoinedBoards(Long userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        ArrayList<BoardSummary> boards = new ArrayList<>();
        for(Board board: user.getJoinedBoards()){
            boards.add(BoardSummary.from(board));
        }
        return ResponseEntity.ok(boards);
    }

    @Transactional
    public ResponseEntity<ArrayList<String>> addCollaborator(CollaboratorRequest collaboratorRequest){
        User user = userRepository.findUserByUsername(collaboratorRequest.collaboratorUsername()).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        Board board = boardRepository.findBoardById(collaboratorRequest.boardId()).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));

        if(board.getCollaborators().contains(user)){
            throw new ResourceAlreadyExistsException("User is already a member of board");
        }

        board.addCollaborator(user);
        ArrayList<String> usernames = (ArrayList<String>) board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList());
        return ResponseEntity.ok(usernames);
    }

    @Transactional
    public ResponseEntity<?> removeCollaborator(CollaboratorRequest collaboratorRequest, Long userAskingForMemberDeleteId){
        User user = userRepository.findUserByUsername(collaboratorRequest.collaboratorUsername()).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        Board board = boardRepository.findBoardById(collaboratorRequest.boardId()).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));
        if(!board.getUser().getId().equals(userAskingForMemberDeleteId) && !Objects.equals(userAskingForMemberDeleteId, user.getId())){
            throw new AccessDeniedException("Only owner of board can delete remove collaborators");
        }
        else if(user.equals(board.getUser())){
            throw new AccessDeniedException("Cannot delete owner from list of collaborators");
        }
        board.removeCollaborator(user);
        return ResponseEntity.noContent().build();
    }
}
