package com.example.flowboard.repository;

import com.example.flowboard.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByCardId(Long cardId);
}
