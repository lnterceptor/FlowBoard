package com.example.flowboard.model;

public record CommentResponse (String content, Long userId, Long commentId, String username){
    public static CommentResponse from(Comment comment){
        return new CommentResponse(comment.getContent(), comment.getUser().getId(), comment.getId(), comment.getUser().getUsername());
    }
}
