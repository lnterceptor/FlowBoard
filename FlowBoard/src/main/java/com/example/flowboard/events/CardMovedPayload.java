package com.example.flowboard.events;

public record CardMovedPayload(Long cardId, Long targetColumnId, Long positionInTargetColumn) {
}
