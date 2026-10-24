package com.example.flowboard.model;

import java.time.LocalDate;
import java.util.Set;

public record CreateCardRequest(String title, String description, LocalDate dueDate, Long columnId, Set<String> assignees, Set<LabelRequest> labels) {
}
