package com.example.flowboard.events;

import com.example.flowboard.model.LabelResponse;

import java.time.LocalDate;
import java.util.ArrayList;

public record CardEditedPayload(String title, String description, LocalDate dueDate, boolean isDone, Long cardId, ArrayList<LabelResponse> labels, ArrayList<String> assignees) {
}
