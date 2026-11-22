package com.example.flowboard.repository;

import com.example.flowboard.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserById(Long userId);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
