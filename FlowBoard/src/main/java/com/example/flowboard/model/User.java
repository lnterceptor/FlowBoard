package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @Column
    private String username;

    @Setter
    @Getter
    @Column
    private String email;

    @Setter
    @Getter
    @Column
    private Instant createdAt;

    @Setter
    @Getter
    @Column
    private String passwordHash;

    @Setter
    @Getter
    @Column
    private Instant updatedAt;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Board> boards = new ArrayList<>();

    public void addBoard(Board board){
        boards.add(board);
        board.setUser(this);
    }

    public void deleteBoard(Board board){
        boards.remove(board);
        board.setUser(null);
    }
    public User(){}

    public User(
            String username,
            String email,
            String passwordHash,
            Instant createdAt
    ){
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        //update id
    }

}
