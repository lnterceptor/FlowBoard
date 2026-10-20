package com.example.flowboard.model;

import java.time.LocalDate;

public record CreateCardRequest(String title, String description, LocalDate dueDate, Long columnId) {
}
