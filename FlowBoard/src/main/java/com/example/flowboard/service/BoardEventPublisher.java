package com.example.flowboard.service;

import com.example.flowboard.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publishBoardDeleted(BoardDeletedEvent board, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, board);
    }

    public void publishCardMoved(CardMovedEvent card, Long boardId){
        messagingTemplate.convertAndSend(
                "/topic/boards/" + boardId, card
        );
    }

    public void publishCardInColumnMoved(CardInColumnMovedEvent card, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, card);
    }

    public void publishCardDeleted(CardDeleted card, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, card);
    }

    public void publicCardCreated(CardCreated card, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, card);
    }
    public void publishCardEdited(CardEdited card, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, card);
    }

    public void publishColumnMoved(ColumnMovedEvent column, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, column);
    }

    public void publishColumnAdded(ColumnAddedEvent column, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, column);
    }

    public void publishColumnDeleted(ColumnDeletedEvent column, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, column);
    }

    public void publishColumnRenamed(ColumnRenamedEvent column, Long boardId){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId, column);
    }

    public void publishCommentAdded(Long cardId, Long boardId, CommentAddedEvent comment){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId + "/cards/" + cardId, comment);
    }

    public void publishCommentDeleted(Long cardId, Long boardId, CommentDeletedEvent comment){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId + "/cards/" + cardId, comment);
    }

    public void publishCommentUpdated(Long cardId, Long boardId, CommentUpdatedEvent comment){
        messagingTemplate.convertAndSend("/topic/boards/" + boardId + "/cards/" + cardId, comment);
    }


}