package com.example.flowboard.service;

import com.example.flowboard.exception.ResourceAlreadyExistsException;
import com.example.flowboard.model.Board;
import com.example.flowboard.model.Label;
import com.example.flowboard.model.LabelRequest;
import com.example.flowboard.model.LabelResponse;
import com.example.flowboard.repository.BoardRepository;
import com.example.flowboard.repository.LabelRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LabelService {
    LabelRepository labelRepository;
    BoardRepository boardRepository;
    LabelService(LabelRepository labelRepository, BoardRepository boardRepository){
        this.labelRepository = labelRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ResponseEntity<LabelResponse> createNewLabel(LabelRequest newLabelRequest, Long boardId){
        Label newLabel = new Label();
        Board board = boardRepository.findBoardById(boardId).orElseThrow();
        if(labelRepository.findLabelByNameAndBoardId(newLabelRequest.name(), boardId).isPresent()){
            throw new ResourceAlreadyExistsException("Label with given name already exist");
        }

        newLabel.setBoard(board);
        newLabel.setName(newLabelRequest.name());
        newLabel.setColor(newLabelRequest.color());
        labelRepository.save(newLabel);
        return ResponseEntity.ok(LabelResponse.from(newLabel));
    }

    @Transactional
    public ResponseEntity<Void> deleteLabel(String name, Long boardId){
        Label label = labelRepository.findLabelByNameAndBoardId(name, boardId).orElseThrow();
        labelRepository.delete(label);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<ArrayList<LabelResponse>> getLabels(Long boardId){
        ArrayList<LabelResponse> labelResponses = new ArrayList<>();
        ArrayList<Label> labels = (ArrayList<Label>) labelRepository.findAllByBoardId(boardId);
        for(Label label: labels){
            labelResponses.add(LabelResponse.from(label));
        }
        return ResponseEntity.ok(labelResponses);
    }
}
