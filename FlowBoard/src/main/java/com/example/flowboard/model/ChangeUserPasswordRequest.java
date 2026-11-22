package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeUserPasswordRequest (@NotBlank @Size(min=5, message="password must have at least 5 letters") String currentPassword, @NotBlank @Size(min=5, message="password must have at least 5 letters") String newPassword){
}
