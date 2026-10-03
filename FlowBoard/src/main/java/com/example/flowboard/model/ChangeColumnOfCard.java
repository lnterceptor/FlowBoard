package com.example.flowboard.model;

public record ChangeColumnOfCard(Long cardId,
                                 Long newColumnId,
                                 Long placementInNewColumn) {
}
