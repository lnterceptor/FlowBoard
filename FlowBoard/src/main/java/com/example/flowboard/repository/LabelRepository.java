package com.example.flowboard.repository;

import com.example.flowboard.model.Label;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends CrudRepository<Label, Long> {
    List<Label> findAllByBoardId(Long boardId);
    Optional<Label> findLabelByNameAndBoardId(String name, Long boardId);
}
