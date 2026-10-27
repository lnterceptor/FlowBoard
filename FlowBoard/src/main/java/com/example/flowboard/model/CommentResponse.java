package com.example.flowboard.model;

public record CommentResponse (String content, Long userId, Long commentId){
    public static CommentResponse from(Comment comment){
        return new CommentResponse(comment.getContent(), comment.getUser().getId(), comment.getId());
    }
}
