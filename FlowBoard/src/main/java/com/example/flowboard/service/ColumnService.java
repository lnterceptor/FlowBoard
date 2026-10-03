package com.example.flowboard.service;

import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.BoardRepository;
import com.example.flowboard.repository.ColumnRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class ColumnService {
    ColumnRepository columnRepository;
    BoardRepository boardRepository;

    public ColumnService(ColumnRepository columnRepository, BoardRepository boardRepository){
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResponseEntity<ArrayList<ColumnResponse>> createColumn(CreateColumnRequest columnRequest){
        BoardColumn column = new BoardColumn();
        column.setTitle(columnRequest.title());

        Board board = boardRepository.findById(columnRequest.boardId()).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));

        column.setBoard(board);
        column.setPositionInBoard((long) board.getColumns().size());

        board.addColumn(column);

        columnRepository.save(column);
        boardRepository.save(board);

        ArrayList<ColumnResponse> returnColumns = new ArrayList<>();

        for(BoardColumn boardColumn: board.getColumns()){
            returnColumns.add(ColumnResponse.from(boardColumn));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(returnColumns);
    }
    public ResponseEntity<BoardColumn> moveColumn(CreateColumnRequest columnRequest){
        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity<BoardColumn> changeColumnName(Long columnId, String changeName){
        BoardColumn column = columnRepository.findBoardColumnsById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));
        column.setTitle(changeName);
        return ResponseEntity.ok(column);
    }

    @Transactional
    public ResponseEntity<?> deleteColumn(Long columnId){
        BoardColumn column = columnRepository.findBoardColumnsById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));

        Board board = column.getBoard();
        long positionOfColumnOnBoard = column.getPositionInBoard();

        board.getColumns().remove((int) positionOfColumnOnBoard);

        for(long i = positionOfColumnOnBoard + 1; i < board.getColumns().size(); i++){
            board.getColumns().get((int) i).setPositionInBoard(i - 1);
        }
        board.setLastUpdateDate(Instant.now());
        columnRepository.delete(column);


        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<?> changeColumnOrder(ChangeObjectOrder changeObjectOrder){
        BoardColumn column = columnRepository.findBoardColumnsById(changeObjectOrder.idOfFirstObject()).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));
        Board board = column.getBoard();

        Long newPosition = changeObjectOrder.positionOfSecondObject();
        if(newPosition >= column.getCards().size() || newPosition < 0) {
            if (newPosition < 0) {
                newPosition = 0L;
            } else {
                newPosition = (long) column.getCards().size() - 1;
            }
        }

        for(BoardColumn tempColumn: board.getColumns()){
            if(tempColumn.getPositionInBoard().equals(newPosition) && !column.equals(tempColumn)){
                tempColumn.setPositionInBoard(column.getPositionInBoard());
                tempColumn.setLastUpdateDate(Instant.now());

                column.setPositionInBoard(newPosition);
                column.setLastUpdateDate(Instant.now());
                break;
            }
        }
        return ResponseEntity.ok(board.getColumns());
    }

    public ResponseEntity<?> getColumnsFromBoard(Long boardId){
        Board board = boardRepository.findBoardById(boardId).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));
        ArrayList<ColumnResponse> columns = new ArrayList<>();
        for(BoardColumn column: board.getColumns()){
            columns.add(ColumnResponse.from(column));
        }
        return ResponseEntity.ok(columns);
    }

}
