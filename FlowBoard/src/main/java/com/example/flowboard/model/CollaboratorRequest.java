package com.example.flowboard.model;

import jakarta.validation.constraints.NotNull;

    public record CollaboratorRequest(@NotNull String collaboratorUsername, @NotNull Long boardId) {
}
