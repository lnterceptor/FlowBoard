package com.example.flowboard.repository;

import com.example.flowboard.model.BoardColumn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColumnRepository extends CrudRepository<BoardColumn, Long> {
    Optional<BoardColumn> findBoardColumnsById(Long columnId);
}
