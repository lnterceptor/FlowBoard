package com.example.flowboard.controller;

import com.example.flowboard.model.CreateUserRequest;
import com.example.flowboard.model.LoginUserRequest;
import com.example.flowboard.model.User;
import com.example.flowboard.repository.UserRepository;
import com.example.flowboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.example.flowboard.service.JwtService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200" })
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private UserRepository userRepository;
    private JwtService jwtService;
    private UserService userService;

    AuthController(UserRepository userRepository, JwtService jwtService, UserService userService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest createUserRequest,
                                             BindingResult result){
        if(result.hasErrors()){
            ResponseEntity.badRequest().body("Errors");
        }
        return userService.createUser(createUserRequest);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest,
                                       BindingResult result){
        if(result.hasErrors()){
            ResponseEntity.badRequest().body("Errors");
        }
        return userService.authenticateUser(loginUserRequest);
    }
}
