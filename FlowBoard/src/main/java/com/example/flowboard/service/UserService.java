package com.example.flowboard.service;

import com.example.flowboard.model.CreateUserRequest;
import com.example.flowboard.model.User;
import com.example.flowboard.model.UserResponse;
import com.example.flowboard.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserResponse> createUser(CreateUserRequest userRequest){
        User user = new User(userRequest.username(), userRequest.email(), userRequest.password(), Instant.now());
//        user.setEmail(userRequest.email());
//        user.setPasswordHash(userRequest.password());//todo: save hashed password instead
  //      user.setUsername(userRequest.username());
    //    user.setCreatedAt(Instant.now());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
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
}
