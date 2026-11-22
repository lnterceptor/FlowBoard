package com.example.flowboard.events;

public record CommentUpdatedEvent(String type, Long commentId, String content) {
}
