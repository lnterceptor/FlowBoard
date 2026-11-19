package com.example.flowboard.events;

public record ColumnAddedEvent(String type, long columnId, long columnPosition, String title) {
}
