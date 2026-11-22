package com.example.flowboard.service;

import com.example.flowboard.repository.UserRepository;
import com.example.flowboard.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    CustomUserDetailsService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                "User not found: " + email
        ));

        UserDetails springUser = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword()).build();
        return springUser;
    }
}
