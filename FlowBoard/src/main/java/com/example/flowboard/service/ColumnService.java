package com.example.flowboard.service;

import com.example.flowboard.events.*;
import com.example.flowboard.exception.ResourceAlreadyExistsException;
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
import java.util.Objects;

@Service
public class ColumnService {
    ColumnRepository columnRepository;
    BoardRepository boardRepository;
    BoardEventPublisher boardEventPublisher;

    public ColumnService(ColumnRepository columnRepository, BoardRepository boardRepository, BoardEventPublisher boardEventPublisher){
        this.columnRepository = columnRepository;
        this.boardRepository = boardRepository;
        this.boardEventPublisher = boardEventPublisher;
    }

    @Transactional
    public ResponseEntity<ColumnResponse> createColumn(CreateColumnRequest columnRequest){
        BoardColumn column = new BoardColumn();
        column.setTitle(columnRequest.title());

        Board board = boardRepository.findById(columnRequest.boardId()).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));
        if(board.getColumns().stream().map(BoardColumn::getTitle).toList().contains(columnRequest.title())){
            throw new ResourceAlreadyExistsException("Column with this name already exist on this board");
        }
        column.setBoard(board);
        column.setPositionInBoard((long) board.getColumns().size());

        board.addColumn(column);

        columnRepository.save(column);
        boardRepository.save(board);

        boardEventPublisher.publishColumnAdded(new ColumnAddedEvent("COLUMN_ADDED", column.getId(), column.getPositionInBoard(), column.getTitle()), board.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ColumnResponse.from(column));
    }

    @Transactional
    public ResponseEntity<ColumnResponse> changeColumnName(Long columnId, String changeName){
        BoardColumn column = columnRepository.findBoardColumnsById(columnId).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));

        if(column.getBoard().getColumns().stream().map(BoardColumn::getTitle).toList().contains(changeName) && !Objects.equals(column.getTitle(), changeName)){
            throw new ResourceAlreadyExistsException("Column with this name already exist on this board");
        }
        column.setTitle(changeName);

        boardEventPublisher.publishColumnRenamed(new ColumnRenamedEvent("COLUMN_RENAMED", column.getId(), column.getTitle()), column.getBoard().getId());
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

        for(Card card: column.getCards()){
            while(!card.getLabels().isEmpty()) {
                Label label = card.getLabels().getFirst();
                card.removeLabel(label);
            }
        }

        columnRepository.delete(column);

        boardEventPublisher.publishColumnDeleted(new ColumnDeletedEvent("COLUMN_DELETED", columnId), board.getId());

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<ArrayList<ColumnResponse>> changeColumnOrder(ChangeObjectOrder changeObjectOrder){
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

        }
        column.setPositionInBoard(newPosition);
        ArrayList<ColumnResponse> columnResponses = new ArrayList<>();
        for(BoardColumn tempColumn: board.getColumns()){
            columnResponses.add(ColumnResponse.from(tempColumn));
        }

        boardEventPublisher.publishColumnMoved(new ColumnMovedEvent("COLUMN_MOVED", column.getId(), column.getPositionInBoard()), board.getId());

        return ResponseEntity.ok(columnResponses);
    }

    public ResponseEntity<ArrayList<ColumnResponse>> getColumnsFromBoard(Long boardId){
        Board board = boardRepository.findBoardById(boardId).orElseThrow(() -> new ResourceNotFoundException("Board does not exist"));
        ArrayList<ColumnResponse> columns = new ArrayList<>();
        for(BoardColumn column: board.getColumns()){
            columns.add(ColumnResponse.from(column));
        }
        return ResponseEntity.ok(columns);
    }

}
