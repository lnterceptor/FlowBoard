package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Board {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private String boardName;
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Instant creationDate;

    @Getter
    @Setter
    Instant lastUpdateDate;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id")
    private User user;


    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "collaborators",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> collaborators = new HashSet<>();

    @Setter
    @Getter
    @OneToMany(
            mappedBy="board",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<BoardColumn> columns = new ArrayList<>();

    public void addCollaborator(User user){
        collaborators.add(user);
        user.getJoinedBoards().add(this);
    }

    public void removeCollaborator(User user){
        collaborators.remove(user);
        user.getJoinedBoards().remove(this);
    }

    public void addColumn(BoardColumn column){
        columns.add(column);
        column.setBoard(this);
    }

    public void removeColumn(BoardColumn column){
        columns.remove(column);
        column.setBoard(null);
    }

}
