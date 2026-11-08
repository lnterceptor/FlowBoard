package com.example.flowboard.controller;

import com.example.flowboard.model.CommentResponse;
import com.example.flowboard.model.CreateCommentRequest;
import com.example.flowboard.service.CommentService;
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
    public ResponseEntity<ArrayList<CommentResponse>> getComments(@PathVariable Long cardId){

        return commentService.getCommentsFromCard(cardId);
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponse> postComment(@PathVariable Long cardId, @RequestBody CreateCommentRequest createCommentRequest){
        return commentService.createComment(cardId, createCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @RequestBody CreateCommentRequest updateComment){
        return commentService.updateComment(commentId, updateComment);
    }
}
