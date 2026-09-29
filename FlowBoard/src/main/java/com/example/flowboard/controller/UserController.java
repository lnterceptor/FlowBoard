package com.example.flowboard.controller;

import com.example.flowboard.model.CreateUserRequest;
import com.example.flowboard.model.UserResponse;
import com.example.flowboard.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest userRequest) {
        //used to temporarily create user, until authentication is done
        return userService.createUser(userRequest);
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<UserResponse>> getUsers(){
        return userService.getUsers();
    }
}
