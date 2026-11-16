package com.example.flowboard.model;

public record UserResponse(
        long userId, String username, String email
) {
    public static UserResponse from(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

}
