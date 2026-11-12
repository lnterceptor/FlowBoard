package com.example.flowboard.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ChangeUserDataRequest(@NotBlank (message = "Email must not me empty")@Email String email, @NotBlank (message = "username must not be empty") @Size(min = 3, message = "username must have at least 3 letters") String username,
                                    @Positive Long userId) {}
