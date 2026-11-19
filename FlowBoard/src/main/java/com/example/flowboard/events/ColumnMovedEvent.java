package com.example.flowboard.events;

public record ColumnMovedEvent(String type, Long columnId, Long newPosition) {
}
