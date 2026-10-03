package com.example.flowboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "cards")
public class Card {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    String assignees; //todo: person/people responsible for doing given assigment, change to a Set after adding members
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
    BoardColumn column;
}
