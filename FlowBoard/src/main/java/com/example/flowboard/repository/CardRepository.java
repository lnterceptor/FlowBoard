package com.example.flowboard.repository;

import com.example.flowboard.model.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Long> {
    Optional<Card> findCardById(Long cardId);
}
