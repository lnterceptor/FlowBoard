package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBoardRequest (
        @NotBlank
        @Size(max = 50)
        String name,

        @Size(max = 500)
        String desc,

        @NotNull
        Long userId
) {}
