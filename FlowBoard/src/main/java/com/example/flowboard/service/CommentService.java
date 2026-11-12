package com.example.flowboard.service;

import com.example.flowboard.exception.AccessDeniedException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.CardRepository;
import com.example.flowboard.repository.CommentRepository;
import com.example.flowboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class CommentService {
    CommentRepository commentRepository;
    CardRepository cardRepository;
    UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, CardRepository cardRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
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
        return ResponseEntity.ok(CommentResponse.from(comment));
    }

    public ResponseEntity<?> deleteComment(Long commentId, Long userId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Cannot find specified comment."));
        if(!Objects.equals(comment.getUser().getId(), userId)){
            throw new AccessDeniedException("You cannot delete comment of different user!");
        }
        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }


}
