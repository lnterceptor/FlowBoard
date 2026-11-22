package com.example.flowboard;


import com.example.flowboard.model.CreateUserRequest;
import com.example.flowboard.model.User;
import com.example.flowboard.model.UserResponse;
import com.example.flowboard.repository.UserRepository;
import com.example.flowboard.service.JwtService;
import com.example.flowboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Test
    public void shouldCorrectlyCreateUser(){
        CreateUserRequest createUserRequest = new CreateUserRequest("Test user", "Test password", "test@email.com");
        when(jwtService.createJwtToken(any())).thenReturn("Example token");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        ResponseEntity<?> response = userService.createUser(createUserRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> responseMap = (Map <String, Object>) response.getBody();
        UserResponse user = (UserResponse) responseMap.get("user");
        assertEquals("test@email.com", user.email());
        assertEquals("Test user", user.username());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertNotEquals("Test password", savedUser.getPassword());
    }

    @Test
    public void shouldNotCreateUserIfEmailDuplicated(){
        CreateUserRequest createUserRequest = new CreateUserRequest("Test user", "Test password", "test@email.com");
        User testUser = new User();
        testUser.setEmail("test@email.com");
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(testUser));

        ResponseEntity<?> response = userService.createUser(createUserRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email address already used", response.getBody());
    }
}
