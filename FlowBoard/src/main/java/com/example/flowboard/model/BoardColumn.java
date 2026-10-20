package com.example.flowboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "columns")
@ToString
public class BoardColumn {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 250)
    private String title;

    @Getter
    @Setter
    private Long positionInBoard;

    @Getter
    @Setter
    Instant lastUpdateDate;

    @Getter
    @Setter
    Instant creationDate;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_name")
    private Board board;

    @OneToMany(mappedBy = "column",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card){
        cards.add(card);
        card.setColumn(this);
    }

    public void removeCard(Card card){
        cards.remove(card);
        card.setColumn(null);
    }
}
