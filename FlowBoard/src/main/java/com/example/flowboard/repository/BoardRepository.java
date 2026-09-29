package com.example.flowboard.repository;

import com.example.flowboard.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findAllByUserId(Long userId);
}
