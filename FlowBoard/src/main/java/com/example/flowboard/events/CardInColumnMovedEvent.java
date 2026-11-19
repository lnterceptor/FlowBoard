package com.example.flowboard.events;

public record CardInColumnMovedEvent (String type, Long cardId, Long newPosition) {
}
