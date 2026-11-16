package com.example.flowboard;


import com.example.flowboard.exception.AccessDeniedException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.CardRepository;
import com.example.flowboard.repository.CommentRepository;
import com.example.flowboard.repository.UserRepository;
import com.example.flowboard.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;


    @Test
    public void shouldCreateComment(){
        Card card = new Card();
        User user = new User();
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L,"Test comment");

        when(cardRepository.findCardById(1L)).thenReturn(Optional.of(card));
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        ResponseEntity<CommentResponse> response = commentService.createComment(1L, createCommentRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test comment", response.getBody().content());
    }
    @Test
    public void shouldNotCreateCommentIfCardCannotBeFound(){
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L,"Test comment");

        when(cardRepository.findCardById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.createComment(1L, createCommentRequest))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Cannot find specified card.");
    }
    @Test
    public void shouldNotCreateCommentIfUserCannotBeFound(){
        Card card = new Card();
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L,"Test comment");

        when(cardRepository.findCardById(1L)).thenReturn(Optional.of(card));
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.createComment(1L, createCommentRequest))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Cannot find specified user.");
    }

    @Test
    public void shouldDeleteComment(){
        User user = new User();
        user.setId(1L);
        Comment comment = new Comment();
        comment.setUser(user);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        ResponseEntity<?> response = commentService.deleteComment(1L, 1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void shouldNotDeleteCommentIfCommentCannotBeFound(){
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->commentService.deleteComment(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Cannot find specified comment.");
    }
    @Test
    public void shouldNotDeleteCommentIfItUserHaveNotCreatedIt(){
        User user = new User();
        user.setId(2L);
        Comment comment = new Comment();
        comment.setUser(user);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        assertThatThrownBy(() ->commentService.deleteComment(1L, 1L))
                .isInstanceOf(AccessDeniedException.class).hasMessage("You cannot delete comment of different user!");
    }

    @Test
    public void shouldUpdateComment(){
        User user = new User();
        user.setId(1L);
        Comment comment = new Comment();
        comment.setUser(user);
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L, "test contents");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        ResponseEntity<CommentResponse> response = commentService.updateComment(1L, createCommentRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test contents", response.getBody().content());
    }
    @Test
    public void shouldNotEditCommentIfCommentCannotBeFound(){
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(1L, "test contents");

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->commentService.updateComment(1L, createCommentRequest))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Cannot find specified comment.");
    }
    @Test
    public void shouldNotEditCommentIfItUserHaveNotCreatedIt(){
        User user = new User();
        user.setId(1L);
        Comment comment = new Comment();
        comment.setUser(user);
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(2L, "test contents");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        assertThatThrownBy(() ->commentService.updateComment(1L, createCommentRequest))
                .isInstanceOf(AccessDeniedException.class).hasMessage("You cannot edit comment of different user!");
    }
}
