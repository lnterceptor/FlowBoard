package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

    public record CollaboratorRequest(@NotBlank String collaboratorUsername, @NotNull Long boardId) {
}
