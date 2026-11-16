package com.example.flowboard;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.Board;
import com.example.flowboard.model.BoardColumn;
import com.example.flowboard.model.ChangeObjectOrder;
import com.example.flowboard.repository.ColumnRepository;
import com.example.flowboard.service.ColumnService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColumnServiceTest {

    @InjectMocks
    private ColumnService columnService;

    @Mock
    private ColumnRepository columnRepository;

    private Board createBoardWithColumns(int amount){
        Board board = new Board();

        for(int i = 0; i < amount; i++){
            BoardColumn column = new BoardColumn();
            column.setBoard(board);
            column.setPositionInBoard((long) board.getColumns().size());
            board.addColumn(column);
        }
        return board;
    }



    @Test
    void shouldSwapFirstAndSecondColumnPosition(){
        Board board = createBoardWithColumns(3);
        BoardColumn column = board.getColumns().getFirst();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 1L));
        assertThat(column.getPositionInBoard()).isEqualTo(1);
        assertThat(board.getColumns().get(1).getPositionInBoard()).isEqualTo(0);
    }

    @Test
    void shouldKeepColumnAtSamePositionWhenSameIndexProvided(){
        Board board = createBoardWithColumns(3);
        BoardColumn column = board.getColumns().getFirst();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 0L));
        assertThat(column.getPositionInBoard()).isEqualTo(0);
    }

    @Test
    void shouldMoveSecondColumnToFirstPositionWhenNegativeIndexProvided(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().get(1);
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), -33L));

        assertThat(column.getPositionInBoard()).isEqualTo(0);
        assertThat(board.getColumns().get(0).getPositionInBoard()).isEqualTo(1);
    }

    @Test
    void shouldMoveFirstColumnToLastPositionWhenIndexOutOfRangeProvided(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().getFirst();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 99L));

        assertThat(column.getPositionInBoard()).isEqualTo(2);
        assertThat(board.getColumns().get(2).getPositionInBoard()).isEqualTo(1);
    }


    @Test
    void shouldNotChangePositionOfFirstColumnWhenNegativeIndexProvided(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().getFirst();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        assertThat(column.getPositionInBoard()).isEqualTo(0);
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), -99L));
        assertThat(column.getPositionInBoard()).isEqualTo(0);
    }

    @Test
    void shouldKeepLastColumnPositionWhenIndexOutOfRangeProvided(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().getLast();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        assertThat(column.getPositionInBoard()).isEqualTo(2);
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 99L));
        assertThat(column.getPositionInBoard()).isEqualTo(2);
    }

    @Test
    void shouldKeepSingleColumnAtPositionZeroWhenIndexOutOfRangeProvided(){
        Board board = createBoardWithColumns(1);

        BoardColumn column = board.getColumns().getLast();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 99L));
        assertThat(column.getPositionInBoard()).isEqualTo(0);
    }

    @Test
    void shouldKeepSingleColumnAtPositionZeroWhenSameIndexProvided(){
        Board board = createBoardWithColumns(1);

        BoardColumn column = board.getColumns().getLast();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 0L));
        assertThat(column.getPositionInBoard()).isEqualTo(0);
    }

    @Test
    void shouldKeepSingleColumnAtPositionZeroWhenNegativeIndexProvided(){
        Board board = createBoardWithColumns(1);

        BoardColumn column = board.getColumns().getLast();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), -99L));
        assertThat(column.getPositionInBoard()).isEqualTo(0);
    }

    @Test
    void shouldMaintainColumnPositionAfterSwap(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().getLast();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 1L));

        assertEquals(Arrays.asList(0L, 2L, 1L), board.getColumns().stream().map(BoardColumn::getPositionInBoard).collect(Collectors.toList()));
    }

    @Test
    void shouldMaintainCorrectPositionsWhenMovingLastColumnToFirst(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().getLast();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), -99L));

        assertEquals(Arrays.asList(1L, 2L, 0L), board.getColumns().stream().map(BoardColumn::getPositionInBoard).collect(Collectors.toList()));
    }


    @Test
    void shouldMaintainCorrectPositionsWhenMovingFirstColumnToOutOfRangeIndex(){
        Board board = createBoardWithColumns(3);

        BoardColumn column = board.getColumns().getFirst();
        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.of(column));
        columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 99L));

        assertEquals(Arrays.asList(2L, 0L, 1L), board.getColumns().stream().map(BoardColumn::getPositionInBoard).collect(Collectors.toList()));
    }




    @Test
    void shouldThrowWhenColumnDoesNotExist(){
        Board board = createBoardWithColumns(3);
        BoardColumn column = board.getColumns().getFirst();

        when(columnRepository.findBoardColumnsById(column.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> columnService.changeColumnOrder(new ChangeObjectOrder(column.getId(), 1L))).
                isInstanceOf(ResourceNotFoundException.class).hasMessage("Column does not exist");
    }
}
