package com.example.flowboard.model;

public record ChangeUserPasswordRequest (String currentPassword, String newPassword){
}
