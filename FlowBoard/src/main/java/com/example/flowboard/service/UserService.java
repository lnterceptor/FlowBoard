package com.example.flowboard.service;

import com.example.flowboard.model.*;
import com.example.flowboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;


    public UserService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public ResponseEntity<?> createUser(CreateUserRequest userRequest){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(userRequest.password());
        User user = new User(userRequest.username(), userRequest.email(), encryptedPassword, Instant.now());

        try{
            Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
            if(existingUser.isPresent()){
               return ResponseEntity.badRequest().body("Email address already used");
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }

        userRepository.save(user);

        String jwtToken = jwtService.createJwtToken(user);
        HashMap response = new HashMap<String, Object>();
        response.put("token", jwtToken);
        response.put("user", UserResponse.from(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<?> authenticateUser(LoginUserRequest login){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.getEmail(),
                    login.getPassword()
            ));

            User user = userRepository.findUserByEmail(login.getEmail()).orElseThrow();
            String jwtToken = jwtService.createJwtToken(user);

            HashMap<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("user", UserResponse.from(user));

            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            System.out.println("Exception");
        }

        return ResponseEntity.badRequest().body("Bad username or password");
    }


    public ResponseEntity<ArrayList<UserResponse>> getUsers(){
        //temporary unnecessary function to check if adding users work
        Iterable<User> iterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        ArrayList<UserResponse> usersResponse = new ArrayList<>();
        iterable.forEach(users::add);
        for (User user : users) {
            usersResponse.add(UserResponse.from(user));
        }

        return ResponseEntity.ok(usersResponse);
    }

    public ResponseEntity<?> deleteUser(Long userId){
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<?> changePassword(ChangeUserPasswordRequest changeUserPasswordRequest, Long userId){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findUserById(userId).orElseThrow();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    changeUserPasswordRequest.currentPassword()
            ));


            if (changeUserPasswordRequest.newPassword().length() < 5) {
                return ResponseEntity.badRequest().body("New password is too short");
            }
            String encryptedNewPassword = bCryptPasswordEncoder.encode(changeUserPasswordRequest.newPassword());
            user.setPassword(encryptedNewPassword);
        }
        catch (Exception e){
            System.out.println("Exception");
        }


        return ResponseEntity.ok("Password correctly changed.");
    }

    @Transactional
    public ResponseEntity<?> changeUserData(ChangeUserDataRequest changeUserDataRequest){
        User user = userRepository.findUserById(changeUserDataRequest.userId()).orElseThrow();
        //todo: validate data
        user.setEmail(changeUserDataRequest.email());
        user.setUsername(changeUserDataRequest.username());
        return ResponseEntity.ok(UserResponse.from(user));
    }
}
