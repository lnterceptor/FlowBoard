package com.example.flowboard.controller;

import com.example.flowboard.model.ChangeUserDataRequest;
import com.example.flowboard.model.ChangeUserPasswordRequest;
import com.example.flowboard.model.CreateUserRequest;
import com.example.flowboard.model.UserResponse;
import com.example.flowboard.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
@RestController
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<UserResponse>> getUsers(){
        return userService.getUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@Positive @PathVariable Long userId){
        return userService.deleteUser(userId);
    }

    @PatchMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody ChangeUserDataRequest changeUserDataRequest){
        return userService.changeUserData(changeUserDataRequest);
    }

    @PatchMapping("/changePassword/{userId}")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangeUserPasswordRequest changeUserPasswordRequest, @Positive @PathVariable Long userId){
        return userService.changePassword(changeUserPasswordRequest, userId);
    }

}
