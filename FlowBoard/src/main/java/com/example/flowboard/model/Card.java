package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "completed", nullable = false)
    boolean done;

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
    private List<User> assignees = new ArrayList<>();

    @Getter
    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Label> labels = new ArrayList<>();

    public void addLabel(Label label){
        labels.add(label);
        label.getCards().add(this);
    }

    public void removeLabel(Label label){
        if(labels.remove(label)){
            label.getCards().remove(this);
        }
    }

    @Getter
    @Setter
    LocalDate dueDate;
    @Getter
    @Setter
    String attachments; //todo: it will also be a Set
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
