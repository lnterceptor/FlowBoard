package com.example.flowboard.repository;

import com.example.flowboard.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserById(Long userId);
    Optional<User> findUserByUsername(String username);
}
