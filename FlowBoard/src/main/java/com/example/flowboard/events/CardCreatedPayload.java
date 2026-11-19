package com.example.flowboard.events;

import com.example.flowboard.model.LabelResponse;

import java.time.LocalDate;
import java.util.ArrayList;

public record CardCreatedPayload (String title, String description, Long positionInColumn,
                                  LocalDate dueDate, ArrayList<LabelResponse> labels, ArrayList<String> assignees, boolean isDone, Long cardId, Long columnId) {
}