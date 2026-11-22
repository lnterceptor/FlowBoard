package com.example.flowboard.model;

import jakarta.validation.constraints.NotNull;

public record ChangeObjectOrder(
        @NotNull Long idOfFirstObject,
        @NotNull Long positionOfSecondObject
) {

}
