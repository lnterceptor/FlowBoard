package com.example.flowboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserResponse(
        long userId, String username, String email
) {
    public static UserResponse from(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

}
