package com.example.flowboard;

import com.example.flowboard.exception.AccessDeniedException;
import com.example.flowboard.exception.ResourceAlreadyExistsException;
import com.example.flowboard.exception.ResourceNotFoundException;
import com.example.flowboard.model.*;
import com.example.flowboard.repository.BoardRepository;
import com.example.flowboard.repository.UserRepository;
import com.example.flowboard.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldCreateBoardAndAddOwnerAsCollaborator(){
        User user = new User();
        user.setUsername("Example user");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<BoardSummary> boardSummary = boardService.createBoard(new CreateBoardRequest("Example board", "Example desc", user.getId()));

        assertNotNull(boardSummary.getBody());
        assertEquals("Example board", boardSummary.getBody().title());
        assertEquals("Example desc", boardSummary.getBody().description());
        assertEquals(user.getId(), boardSummary.getBody().ownerId());
        assertEquals(List.of("Example user"), boardSummary.getBody().collaborators());
    }

    @Test
    void shouldNotCreateBoardIfUserDoesNotExist(){
        User user = new User();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boardService.createBoard(new CreateBoardRequest("Example board", "Example desc", user.getId())))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("User does not exist");
    }


    @Test
    void shouldAddCollaborator(){
        Board board = new Board();
        User user = new User();
        user.setUsername("Example user");
        CollaboratorRequest collaboratorRequest = new CollaboratorRequest("Example user", board.getId());
        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        boardService.addCollaborator(collaboratorRequest);
        assertEquals(List.of("Example user"), board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList()));
    }



    @Test
    void shouldNotAddDuplicateCollaborator(){
        Board board = new Board();
        User user = new User();
        user.setUsername("Example user");
        board.addCollaborator(user);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest("Example user", board.getId());
        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> boardService.addCollaborator(collaboratorRequest)).isInstanceOf(ResourceAlreadyExistsException.class).hasMessage("User is already a member of board");
    }

    @Test
    void shouldThrowErrorIfCannotFindBoard(){
        Board board = new Board();
        User user = new User();
        user.setUsername("Example user");
        CollaboratorRequest collaboratorRequest = new CollaboratorRequest("Example user", board.getId());

        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.empty());
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> boardService.addCollaborator(collaboratorRequest)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Board does not exist");
   }


    @Test
    void shouldThrowErrorIfCannotFindUser(){
        Board board = new Board();
        User user = new User();
        user.setUsername("Example user");
        CollaboratorRequest collaboratorRequest = new CollaboratorRequest("Example user", board.getId());

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boardService.addCollaborator(collaboratorRequest)).isInstanceOf(ResourceNotFoundException.class).hasMessage("User does not exist");
    }

    @Test
    void shouldRemoveCollaborator(){
        User owner = new User();
        owner.setUsername("Owner");
        owner.setId(1L);
        User collaborator = new User();
        collaborator.setId(2L);
        collaborator.setUsername("Collaborator");
        Board board = new Board();
        board.setUser(owner);
        board.addCollaborator(collaborator);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest("Collaborator", board.getId());

        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findUserByUsername(collaborator.getUsername())).thenReturn(Optional.of(collaborator));

        boardService.removeCollaborator(collaboratorRequest, owner.getId());
        assertTrue(board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList()).isEmpty());
    }


    @Test
    void shouldThrowErrorIfNotOwnerTriesToRemoveCollaborator(){
        User owner = new User();
        owner.setUsername("Owner");
        owner.setId(1L);
        User collaborator = new User();
        collaborator.setId(2L);
        collaborator.setUsername("Collaborator");
        User collaboratorToDelete = new User();
        collaboratorToDelete.setId(3L);
        collaboratorToDelete.setUsername("Collaborator to delete");
        Board board = new Board();
        board.setUser(owner);
        board.addCollaborator(owner);
        board.addCollaborator(collaborator);
        board.addCollaborator(collaboratorToDelete);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest(collaboratorToDelete.getUsername(), board.getId());

        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findUserByUsername(collaboratorToDelete.getUsername())).thenReturn(Optional.of(collaboratorToDelete));

        assertThatThrownBy(() -> boardService.removeCollaborator(collaboratorRequest, collaborator.getId()))
                .isInstanceOf(AccessDeniedException.class).hasMessage("Only owner of board can delete remove collaborators");
        assertEquals(List.of("Owner","Collaborator", "Collaborator to delete"),board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList()));
    }

    @Test
    void shouldThrowErrorIfCollaboratorTriesToRemoveOwner(){
        User owner = new User();
        owner.setUsername("Owner");
        owner.setId(1L);
        User collaborator = new User();
        collaborator.setId(2L);
        collaborator.setUsername("Collaborator");

        Board board = new Board();
        board.setUser(owner);
        board.addCollaborator(owner);
        board.addCollaborator(collaborator);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest(owner.getUsername(), board.getId());

        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findUserByUsername(owner.getUsername())).thenReturn(Optional.of(owner));

        assertThatThrownBy(() -> boardService.removeCollaborator(collaboratorRequest, collaborator.getId()))
                .isInstanceOf(AccessDeniedException.class).hasMessage("Only owner of board can delete remove collaborators");
        assertEquals(List.of("Owner", "Collaborator"),board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList()));
    }

    @Test
    void shouldThrowErrorIfOwnerTriesToRemoveOwner(){
        User owner = new User();
        owner.setUsername("Owner");
        owner.setId(1L);

        Board board = new Board();
        board.setUser(owner);
        board.addCollaborator(owner);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest(owner.getUsername(), board.getId());

        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findUserByUsername(owner.getUsername())).thenReturn(Optional.of(owner));

        assertThatThrownBy(() -> boardService.removeCollaborator(collaboratorRequest, owner.getId()))
                .isInstanceOf(AccessDeniedException.class).hasMessage("Cannot delete owner from list of collaborators");
        assertEquals(List.of("Owner"),board.getCollaborators().stream().map(User::getUsername).collect(Collectors.toList()));
    }


    @Test
    void shouldThrowErrorIfUserToDeleteIsNotFound(){
        User owner = new User();
        owner.setUsername("Owner");
        owner.setId(1L);

        Board board = new Board();
        board.setUser(owner);
        board.addCollaborator(owner);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest("Not existing user", board.getId());

        when(userRepository.findUserByUsername("Not existing user")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boardService.removeCollaborator(collaboratorRequest, owner.getId()))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("User does not exist");
    }


    @Test
    void shouldThrowErrorIfBoardIsNotFound(){
        User owner = new User();
        owner.setUsername("Owner");
        owner.setId(1L);

        User collaborator = new User();
        collaborator.setId(2L);
        collaborator.setUsername("Collaborator");

        Board board = new Board();
        board.setUser(owner);
        board.addCollaborator(owner);

        CollaboratorRequest collaboratorRequest = new CollaboratorRequest(collaborator.getUsername(), board.getId());

        when(boardRepository.findBoardById(board.getId())).thenReturn(Optional.empty());
        when(userRepository.findUserByUsername(collaborator.getUsername())).thenReturn(Optional.of(collaborator));

        assertThatThrownBy(() -> boardService.removeCollaborator(collaboratorRequest, owner.getId()))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Board does not exist");
    }
}
