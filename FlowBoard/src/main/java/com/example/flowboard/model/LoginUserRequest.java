package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public record LoginUserRequest (

        @NotBlank
        @Size(max = 50)
        @Getter
        @Setter
        String email,

        @NotBlank
        @Size(max = 50)
        @Getter
        @Setter
        String password
){}