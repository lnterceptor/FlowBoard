package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateColumnRequest(@NotBlank (message = "Column title cannot be empty")
                                  @Size(max = 250, min=3, message = "Column title needs to have at least 3")
                                  String title,

                                  @NotNull
                                  Long boardId
) {

}
