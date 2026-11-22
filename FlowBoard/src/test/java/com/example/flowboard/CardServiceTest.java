package com.example.flowboard;

import com.example.flowboard.exception.InvalidMoveCardException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.CardRepository;
import com.example.flowboard.repository.ColumnRepository;
import com.example.flowboard.service.CardService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private EntityManager entityManager;


    private Board createBoardWithColumnsAndCards(int amount){
        Board board = new Board();

        for(int i = 0; i < amount; i++){
            BoardColumn column = new BoardColumn();
            column.setId((long) i );
            column.setBoard(board);
            column.setPositionInBoard((long) board.getColumns().size());
            for(int j = 0; j < amount; j++){
                Card card = new Card();
                card.setId((long) i * amount + j);
                card.setPositionInColumn((long) j);
                column.addCard(card);
            }
            board.addColumn(column);
        }
        return board;
    }

    private BoardColumn createColumnWithCardsWithProvidedRangeOfIndexes(long from, long to){
        BoardColumn column = new BoardColumn();
        long i = 0;
        for(;from< to; from ++){
            Card card = new Card();
            card.setId(from);
            card.setPositionInColumn(i);
            column.addCard(card);
            i++;
        }
        return column;
    }


    private BoardColumn createColumnWithCards(int amount){
        BoardColumn column = new BoardColumn();

        for(long i = 0; i < amount; i++){
            Card card = new Card();
            card.setId(i);
            card.setPositionInColumn(i);
            column.addCard(card);
        }
        return column;
    }



    //tests for changeOrderInColumn

    @Test
    public void shouldSwapFirstAndSecondCardPosition(){
        BoardColumn boardColumn = createColumnWithCards(3);
        Card card = boardColumn.getCards().get(1);
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(1L, 0L);

        when(cardRepository.findCardById(1L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);
        assertEquals(Arrays.asList(1L, 0L, 2L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    public void shouldKeepCardsInTheSamePosition(){
        BoardColumn boardColumn = createColumnWithCards(3);
        Card card = boardColumn.getCards().get(1);
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(1L, 1L);

        when(cardRepository.findCardById(1L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);
        assertEquals(Arrays.asList(0L, 1L, 2L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    public void shouldMoveSecondCardToFirstPositionWhenNegativeIndexProvided(){
        BoardColumn boardColumn = createColumnWithCards(3);
        Card card = boardColumn.getCards().get(1);
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(1L, -99L);

        when(cardRepository.findCardById(1L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(Arrays.asList(1L, 0L, 2L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    public void shouldMoveFirstCardToLastPositionWhenIndexOutOfRangeProvided(){
        BoardColumn boardColumn = createColumnWithCards(3);
        Card card = boardColumn.getCards().getFirst();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, 99L);

        when(cardRepository.findCardById(0L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(Arrays.asList(2L, 0L, 1L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

    }

    @Test
    void shouldNotChangePositionOfFirstCardWhenNegativeIndexProvided(){
        BoardColumn boardColumn = createColumnWithCards(3);
        Card card = boardColumn.getCards().getFirst();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, -99L);

        when(cardRepository.findCardById(0L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(Arrays.asList(0L, 1L, 2L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

    }
    @Test
    void shouldKeepLastCardPositionWhenIndexOutOfRangeProvided(){
        BoardColumn boardColumn = createColumnWithCards(3);
        Card card = boardColumn.getCards().getLast();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(2L, 99L);

        when(cardRepository.findCardById(2L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(Arrays.asList(0L, 1L, 2L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

    }

    @Test
    void shouldKeepSingleCardAtPositionZeroWhenIndexOutOfRangeProvided(){
        BoardColumn boardColumn = createColumnWithCards(1);
        Card card = boardColumn.getCards().getLast();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, 99L);

        when(cardRepository.findCardById(0L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(List.of(0L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldKeepSingleCardAtPositionZeroWhenSameIndexProvided(){
        BoardColumn boardColumn = createColumnWithCards(1);
        Card card = boardColumn.getCards().getLast();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, 0L);

        when(cardRepository.findCardById(0L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(List.of(0L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldKeepSingleCardAtPositionZeroWhenNegativeIndexProvided(){
        BoardColumn boardColumn = createColumnWithCards(1);
        Card card = boardColumn.getCards().getLast();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, -99L);

        when(cardRepository.findCardById(0L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(List.of(0L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldMaintainCardPositionAfterSwap(){
        BoardColumn boardColumn = createColumnWithCards(5);
        Card card = boardColumn.getCards().getFirst();
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, 99L);

        when(cardRepository.findCardById(0L)).thenReturn(Optional.ofNullable(card));

        cardService.changeOrderInColumn(changeObjectOrder);

        assertEquals(Arrays.asList(4L, 0L, 1L, 2L, 3L), boardColumn.getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldThrowWhenCardDoesNotExist(){
        ChangeObjectOrder changeObjectOrder = new ChangeObjectOrder(0L, 99L);
        when(cardRepository.findCardById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.changeOrderInColumn(changeObjectOrder))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Card does not exist");
    }


    //tests for moveCardToDifferentColumn
    /*
        Card card = cardRepository.findCardById(newPlacement.cardId()).orElseThrow(() -> new ResourceNotFoundException("Card with id: " + newPlacement.cardId() + " not found"));
        BoardColumn column = columnRepository.findBoardColumnsById(card.getColumn().getId()).orElseThrow(() -> new ResourceNotFoundException("Column does not exist"));
        BoardColumn newColumn = columnRepository.findBoardColumnsById(newPlacement.newColumnId()).orElseThrow(() -> new ResourceNotFoundException("Target column does not exist"));

        * */
    @Test
    void shouldMoveSecondCardFromSecondColumnToThirdPositionInFirstColumn(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(4L, 0L, 2L);
        Board board = createBoardWithColumnsAndCards(3);
        Card card = board.getColumns().get(1).getCards().get(1);

        when(cardRepository.findCardById(4L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));
        when(columnRepository.findBoardColumnsById(0L)).thenReturn(Optional.ofNullable(board.getColumns().getFirst()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(Arrays.asList(0L, 1L, 2L, 4L), board.getColumns().get(0).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(0L, 1L, 3L, 2L), board.getColumns().get(0).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(3L, 5L), board.getColumns().get(1).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(0L, 1L), board.getColumns().get(1).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }


    @Test
    void shouldMoveSecondCardFromSecondColumnToFirstPositionInFirstColumnWhenNegativeIndexProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(4L, 0L, -99L);
        Board board = createBoardWithColumnsAndCards(3);
        Card card = board.getColumns().get(1).getCards().get(1);

        when(cardRepository.findCardById(4L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));
        when(columnRepository.findBoardColumnsById(0L)).thenReturn(Optional.ofNullable(board.getColumns().getFirst()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(Arrays.asList(0L, 1L, 2L, 4L), board.getColumns().get(0).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(1L, 2L, 3L, 0L), board.getColumns().get(0).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(3L, 5L), board.getColumns().get(1).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(0L, 1L), board.getColumns().get(1).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }


    @Test
    void shouldMoveSecondCardFromSecondColumnToFirstPositionInFirstColumnWhenIndexOutOfRangeProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(4L, 0L, 99L);
        Board board = createBoardWithColumnsAndCards(3);
        Card card = board.getColumns().get(1).getCards().get(1);

        when(cardRepository.findCardById(4L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));
        when(columnRepository.findBoardColumnsById(0L)).thenReturn(Optional.ofNullable(board.getColumns().getFirst()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(Arrays.asList(0L, 1L, 2L, 4L), board.getColumns().get(0).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(0L, 1L, 2L, 3L), board.getColumns().get(0).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(3L, 5L), board.getColumns().get(1).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(0L, 1L), board.getColumns().get(1).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }



    @Test
    void shouldNotMoveWhenSamePositionProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(4L, 1L, 1L);
        Board board = createBoardWithColumnsAndCards(3);
        Card card = board.getColumns().get(1).getCards().get(1);

        when(cardRepository.findCardById(4L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(1, card.getColumn().getId());
        assertEquals(1, card.getPositionInColumn());
    }

    @Test
    void shouldMoveToLastPositionWhenIndexOutOfRangeProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(4L, 1L, 99L);
        Board board = createBoardWithColumnsAndCards(3);
        Card card = board.getColumns().get(1).getCards().get(1);

        when(cardRepository.findCardById(4L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(Arrays.asList(3L, 5L, 4L), board.getColumns().get(1).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(0L, 1L, 2L), board.getColumns().get(1).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

    }

    @Test
    void shouldMoveToFirstPositionWhenNegativeIndexProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(4L, 1L, -99L);
        Board board = createBoardWithColumnsAndCards(3);
        Card card = board.getColumns().get(1).getCards().get(1);

        when(cardRepository.findCardById(4L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(Arrays.asList(3L, 5L, 4L), board.getColumns().get(1).getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(Arrays.asList(1L, 2L, 0L), board.getColumns().get(1).getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));

    }

    @Test
    void shouldKeepSingleCardInSingleColumnAtPositionZeroWhenSameIndexProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 0L, 0L);
        Board board = createBoardWithColumnsAndCards(1);
        Card card = board.getColumns().getFirst().getCards().getFirst();

        when(cardRepository.findCardById(0L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(List.of(0L), board.getColumns().getFirst().getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(List.of(0L), board.getColumns().getFirst().getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldKeepSingleCardInSingleColumnAtPositionZeroWhenNegativeIndexProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 0L, -99L);
        Board board = createBoardWithColumnsAndCards(1);
        Card card = board.getColumns().getFirst().getCards().getFirst();

        when(cardRepository.findCardById(0L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(List.of(0L), board.getColumns().getFirst().getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(List.of(0L), board.getColumns().getFirst().getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldKeepSingleCardInSingleColumnAtPositionZeroWhenIndexOutOfRangeProvided(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 0L, 99L);
        Board board = createBoardWithColumnsAndCards(1);
        Card card = board.getColumns().getFirst().getCards().getFirst();

        when(cardRepository.findCardById(0L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));

        cardService.moveCardToDifferentColumn(changeColumnOfCard);

        assertEquals(List.of(0L), board.getColumns().getFirst().getCards().stream().map(Card::getId)
                .collect(Collectors.toList()));

        assertEquals(List.of(0L), board.getColumns().getFirst().getCards().stream().map(Card::getPositionInColumn)
                .collect(Collectors.toList()));
    }

    @Test
    void shouldThrowWhenCardBeingMovedDoesNotExist(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 0L, 99L);
        when(cardRepository.findCardById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cardService.moveCardToDifferentColumn(changeColumnOfCard))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Card with id: 0 not found");
    }

    @Test
    void shouldThrowWhenSourceColumnDoesNotExist(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 0L, 99L);

        Board board = createBoardWithColumnsAndCards(1);
        Card card = board.getColumns().getFirst().getCards().getFirst();

        when(cardRepository.findCardById(0L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.moveCardToDifferentColumn(changeColumnOfCard))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Source column does not exist");
    }


    @Test
    void shouldThrowWhenTargetColumnDoesNotExist(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 1L, 99L);

        Board board = createBoardWithColumnsAndCards(1);
        Card card = board.getColumns().getFirst().getCards().getFirst();

        when(cardRepository.findCardById(0L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));
        when(columnRepository.findBoardColumnsById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.moveCardToDifferentColumn(changeColumnOfCard))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Target column does not exist");
    }

    @Test
    void shouldThrowErrorWhenTryingToMoveCardToColumnOnDifferentBoard(){
        ChangeColumnOfCard changeColumnOfCard = new ChangeColumnOfCard(0L, 4L, 0L);

        Board board = createBoardWithColumnsAndCards(3);
        Board secondBoard = new Board();
        secondBoard.addColumn(createColumnWithCardsWithProvidedRangeOfIndexes(9,12));
        secondBoard.getColumns().getFirst().setId(4L);
        Card card = board.getColumns().getFirst().getCards().getFirst();

        when(cardRepository.findCardById(0L)).thenReturn(Optional.of(card));
        when(columnRepository.findBoardColumnsById(card.getColumn().getId())).thenReturn(Optional.ofNullable(card.getColumn()));
        when(columnRepository.findBoardColumnsById(4L)).thenReturn(Optional.ofNullable(secondBoard.getColumns().getFirst()));

        assertThatThrownBy(() -> cardService.moveCardToDifferentColumn(changeColumnOfCard))
                .isInstanceOf(InvalidMoveCardException.class).hasMessage("Cannot move card to a different board");
    }
}
