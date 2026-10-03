package com.example.flowboard.repository;

import com.example.flowboard.model.BoardColumn;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface ColumnRepository extends CrudRepository<BoardColumn, Long> {
    Optional<BoardColumn> findBoardColumnsById(Long columnId);
}
