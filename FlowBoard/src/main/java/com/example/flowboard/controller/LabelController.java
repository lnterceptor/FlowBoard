package com.example.flowboard.controller;

import com.example.flowboard.model.LabelRequest;
import com.example.flowboard.model.LabelResponse;
import com.example.flowboard.service.LabelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/boards/{boardId}/labels")
public class LabelController {
    LabelService labelService;

    LabelController(LabelService labelService){
        this.labelService = labelService;
    }

    @PostMapping("/")
    public ResponseEntity<LabelResponse> createLabel(@RequestBody LabelRequest labelRequest, @PathVariable Long boardId){
        return labelService.createNewLabel(labelRequest, boardId);
    }

    @DeleteMapping("/{labelName}")
    public ResponseEntity<Void> createLabel(@PathVariable String labelName, @PathVariable Long boardId){
        return labelService.deleteLabel(labelName, boardId);
    }

    @GetMapping("/")
    public ResponseEntity<ArrayList<LabelResponse>> getLabels(@PathVariable Long boardId){
        return labelService.getLabels(boardId);
    }

}
