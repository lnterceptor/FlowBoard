package com.example.flowboard.events;

public record ColumnRenamedEvent(String type, Long columnId, String newName) {
}
