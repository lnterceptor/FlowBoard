package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateCardRequest(@NotBlank (message = "Card needs to have a title") String title, String description, @NotNull(message = "Card needs to have a due time") LocalDate dueDate, Long columnId, Set<String> assignees, Set<LabelRequest> labels, @NotNull boolean done) {
}
