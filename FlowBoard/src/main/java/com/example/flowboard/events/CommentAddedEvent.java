package com.example.flowboard.events;

public record CommentAddedEvent(String type, String content, Long userId,Long commentId, String username) {
}
