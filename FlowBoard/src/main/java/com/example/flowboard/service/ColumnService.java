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
import java.util.Comparator;

@Service
public class ColumnService {
    ColumnRepository columnRepository;
    BoardRepository boardRepository;

    public ColumnService(ColumnRepository columnRepository, BoardRepository boardRepository){
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResponseEntity<ColumnResponse> createColumn(CreateColumnRequest columnRequest){
        BoardColumn column = new BoardColumn();
        column.setTitle(columnRequest.title());

        Board board = boardRepository.findById(columnRequest.boardId()).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));

        column.setBoard(board);
        column.setPositionInBoard((long) board.getColumns().size());

        board.addColumn(column);

        columnRepository.save(column);
        boardRepository.save(board);

        /*ArrayList<ColumnResponse> returnColumns = new ArrayList<>();

        for(BoardColumn boardColumn: board.getColumns()){
            returnColumns.add(ColumnResponse.from(boardColumn));
        }*/

        return ResponseEntity.status(HttpStatus.CREATED).body(ColumnResponse.from(column));
    }

    public ResponseEntity<BoardColumn> moveColumn(CreateColumnRequest columnRequest){
        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity<ColumnResponse> changeColumnName(Long columnId, String changeName){
        BoardColumn column = columnRepository.findBoardColumnsById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));
        column.setTitle(changeName);
        return ResponseEntity.ok(ColumnResponse.from(column));
    }

    @Transactional
    public ResponseEntity<?> deleteColumn(Long columnId){
        BoardColumn column = columnRepository.findBoardColumnsById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));

        Board board = column.getBoard();
        long positionOfColumnOnBoard = column.getPositionInBoard();
        board.removeColumn(column);

        for(BoardColumn tempColumn: board.getColumns()){
            if(tempColumn.getPositionInBoard() > positionOfColumnOnBoard) {
                tempColumn.setPositionInBoard(tempColumn.getPositionInBoard() - 1);
            }
        }
        board.setLastUpdateDate(Instant.now());
        columnRepository.delete(column);


        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<ArrayList<ColumnResponse>> changeColumnOrder(ChangeObjectOrder changeObjectOrder){
        System.out.println(changeObjectOrder.idOfFirstObject()+" "+ changeObjectOrder.positionOfSecondObject());
        BoardColumn column = columnRepository.findBoardColumnsById(changeObjectOrder.idOfFirstObject()).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));
        Board board = column.getBoard();

        Long newPosition = changeObjectOrder.positionOfSecondObject();
        if(newPosition >= board.getColumns().size() || newPosition < 0) {
            if (newPosition <= 0) {
                newPosition = 0L;
            } else {
                newPosition = (long) board.getColumns().size() - 1;
            }
        }

        for(BoardColumn tempColumn: board.getColumns()){
            if(newPosition < column.getPositionInBoard() && tempColumn.getPositionInBoard() >= newPosition
                && tempColumn.getPositionInBoard() < column.getPositionInBoard()){
                tempColumn.setPositionInBoard(tempColumn.getPositionInBoard() + 1);
                tempColumn.setLastUpdateDate(Instant.now());
            }
            else if(newPosition > column.getPositionInBoard() && tempColumn.getPositionInBoard() <= newPosition
                && tempColumn.getPositionInBoard() > column.getPositionInBoard()){
                tempColumn.setPositionInBoard(tempColumn.getPositionInBoard() - 1);
                tempColumn.setLastUpdateDate(Instant.now());

            }
            /*if(tempColumn.getPositionInBoard().equals(newPosition) && !column.equals(tempColumn)){
                System.out.println("change done");
                tempColumn.setPositionInBoard(column.getPositionInBoard());
                tempColumn.setLastUpdateDate(Instant.now());

                column.setPositionInBoard(newPosition);
                column.setLastUpdateDate(Instant.now());
                break;
            }*/
        }
        column.setPositionInBoard(newPosition);
        ArrayList<ColumnResponse> columnResponses = new ArrayList<>();
        for(BoardColumn tempColumn: board.getColumns()){
            columnResponses.add(ColumnResponse.from(tempColumn));
        }
            return ResponseEntity.ok(columnResponses);
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
