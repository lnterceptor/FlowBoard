package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;

public record LabelRequest(@NotBlank (message = "Label name cannot be blank") String name,@NotBlank String color) {
}
