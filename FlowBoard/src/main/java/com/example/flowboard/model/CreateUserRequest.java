package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest (

    @NotBlank
    @Size(max = 50)
    String username,

    @NotBlank
    @Size(max = 50)
    String password,

    @NotBlank
    @Size(max = 50)
    String email
){}
