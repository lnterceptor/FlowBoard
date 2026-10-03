package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateColumnRequest(@NotBlank
                                  @Size(max = 250)
                                  String title,

                                  @NotNull
                                  Long boardId
) {

}
