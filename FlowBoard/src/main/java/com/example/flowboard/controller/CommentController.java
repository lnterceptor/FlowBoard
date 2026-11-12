package com.example.flowboard.controller;

import com.example.flowboard.model.CommentResponse;
import com.example.flowboard.model.CreateCommentRequest;
import com.example.flowboard.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cards/{cardId}/comments")
public class CommentController {
    CommentService commentService;
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<CommentResponse>> getComments(@Positive @PathVariable Long cardId){

        return commentService.getCommentsFromCard(cardId);
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponse> postComment(@Positive @PathVariable Long cardId, @Valid @RequestBody CreateCommentRequest createCommentRequest){
        return commentService.createComment(cardId, createCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@Positive @PathVariable Long commentId,@Positive @RequestParam Long userId){
        return commentService.deleteComment(commentId, userId);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@Positive @PathVariable Long commentId,@Valid @RequestBody CreateCommentRequest updateComment){
        return commentService.updateComment(commentId, updateComment);
    }
}
