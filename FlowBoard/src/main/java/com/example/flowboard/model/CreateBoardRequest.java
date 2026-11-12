package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBoardRequest (
        @NotBlank
        @Size(max = 50, min=3, message = "Board must have at least 3 letters and not more than 50")
        String name,

        @Size(max = 500, message = "Board description must have less than 500 characters")
        String desc,

        @NotNull
        Long userId
) {}
