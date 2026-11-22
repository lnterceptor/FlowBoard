package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String username;

    @Setter
    @Getter
    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Getter
    @Column
    private Instant createdAt;

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

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

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUser(this);
    }

    @Getter
    @ManyToMany(mappedBy = "collaborators", fetch = FetchType.LAZY)
    private Set<Board> joinedBoards = new HashSet<>();

    @Getter
    @ManyToMany(mappedBy = "assignees", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Card> assignedCards = new ArrayList<>();

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
            String password,
            Instant createdAt
    ){
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
