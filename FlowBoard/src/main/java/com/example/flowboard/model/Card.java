package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "cards")
@ToString
public class Card {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "card_assignees",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<User> assignees = new HashSet<>();

    @Getter
    @Setter
    LocalDate dueDate;
    @Getter
    @Setter
    String attachments; //todo: it will also be a Set
    @Getter
    @Setter
    String labels; //todo: it will also be a Set with labels
    @Getter
    @Setter
    Long positionInColumn;

    @Getter
    @Setter
    Instant lastUpdateDate;

    @Getter
    @Setter
    Instant creationDate;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="column_id")
    @ToString.Exclude
    BoardColumn column;

    public void addAssignee(User user) {
        this.assignees.add(user);
        user.getAssignedCards().add(this);
    }

    public void removeAssignee(User user){
        this.assignees.remove(user);
        user.getAssignedCards().remove(this);
    }
}
