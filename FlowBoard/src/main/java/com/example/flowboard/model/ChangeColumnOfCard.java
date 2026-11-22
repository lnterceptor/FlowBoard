package com.example.flowboard.model;

import jakarta.validation.constraints.NotNull;

public record ChangeColumnOfCard(@NotNull Long cardId,
                                 @NotNull Long newColumnId,
                                 @NotNull Long placementInNewColumn) {
}
