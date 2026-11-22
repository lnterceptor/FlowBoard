package com.example.flowboard.service;

import com.example.flowboard.events.CommentAddedEvent;
import com.example.flowboard.events.CommentDeletedEvent;
import com.example.flowboard.events.CommentUpdatedEvent;
import com.example.flowboard.exception.AccessDeniedException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.CardRepository;
import com.example.flowboard.repository.CommentRepository;
import com.example.flowboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class CommentService {
    BoardEventPublisher boardEventPublisher;
    CommentRepository commentRepository;
    CardRepository cardRepository;
    UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, CardRepository cardRepository, UserRepository userRepository, BoardEventPublisher boardEventPublisher){
        this.commentRepository = commentRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.boardEventPublisher = boardEventPublisher;
    }

    public ResponseEntity<ArrayList<CommentResponse>> getCommentsFromCard(Long cardId){
        ArrayList<Comment> comments = (ArrayList<Comment>) commentRepository.findAllByCardId(cardId);
        ArrayList<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment comment: comments){
            commentResponses.add(CommentResponse.from(comment));
        }
        return ResponseEntity.ok(commentResponses);
    }

    @Transactional
    public ResponseEntity<CommentResponse> createComment (Long cardId, CreateCommentRequest createCommentRequest){
        Card card = cardRepository.findCardById(cardId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified card."));
        User user = userRepository.findUserById(createCommentRequest.userId()).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified user."));
        Comment comment = new Comment();
        comment.setCreatedAt(Instant.now());
        comment.setUser(user);
        comment.setCard(card);
        comment.setContent(createCommentRequest.content());
        commentRepository.save(comment);

        boardEventPublisher.publishCommentAdded(
                card.getId(), card.getColumn().getBoard().getId(), new CommentAddedEvent("COMMENT_ADDED", comment.getContent(),comment.getUser().getId(), comment.getId(), comment.getUser().getUsername()));


        return ResponseEntity.ok(CommentResponse.from(comment));
    }

    @Transactional
    public ResponseEntity<CommentResponse> updateComment(Long commentId, CreateCommentRequest updatedComment){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified comment."));
        if(!Objects.equals(comment.getUser().getId(), updatedComment.userId())){
            throw new AccessDeniedException("You cannot edit comment of different user!");
        }

        comment.setContent(updatedComment.content());
        comment.setLastUpdateDate(Instant.now());

        boardEventPublisher.publishCommentUpdated(
                comment.getCard().getId(), comment.getCard().getColumn().getBoard().getId(), new CommentUpdatedEvent("COMMENT_UPDATED", comment.getId(), comment.getContent()));

        return ResponseEntity.ok(CommentResponse.from(comment));
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId, Long userId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified comment."));
        if(!Objects.equals(comment.getUser().getId(), userId)){
            throw new AccessDeniedException("You cannot delete comment of different user!");
        }
        commentRepository.delete(comment);

        boardEventPublisher.publishCommentDeleted(comment.getCard().getId(), comment.getCard().getColumn().getBoard().getId(), new CommentDeletedEvent("COMMENT_DELETED", commentId));

        return ResponseEntity.noContent().build();
    }


}
