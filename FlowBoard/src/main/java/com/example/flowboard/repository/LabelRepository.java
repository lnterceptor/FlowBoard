package com.example.flowboard.repository;

import com.example.flowboard.model.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {
    List<Label> findAllByBoardId(Long boardId);
    Optional<Label> findLabelByNameAndBoardId(String name, Long boardId);
}
