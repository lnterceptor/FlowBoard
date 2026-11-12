package com.example.flowboard.controller;

import com.example.flowboard.model.ChangeObjectOrder;
import com.example.flowboard.model.ColumnResponse;
import com.example.flowboard.model.CreateColumnRequest;
import com.example.flowboard.service.ColumnService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/columns")
public class ColumnController {
    ColumnService columnService;

    public ColumnController(ColumnService columnService){
        this.columnService = columnService;
    }

    @PostMapping("/")
    public ResponseEntity<ColumnResponse> createColumn(@Valid @RequestBody CreateColumnRequest columnRequest){
        return columnService.createColumn(columnRequest);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<?> deleteColumn(@Positive @PathVariable Long columnId){
        return columnService.deleteColumn(columnId);
    }
    @PatchMapping("/{columnId}")
    public ResponseEntity<ColumnResponse> changeColumnName(@Positive @PathVariable Long columnId, @NotBlank(message = "Column title cannot be empty") @Size(min=3, message="Column title needs to have at least 3 characters") @RequestBody String columnName){
        return columnService.changeColumnName(columnId, columnName);
    }

    @PatchMapping("/reorder")
    public ResponseEntity<?> changeColumnOrder(@Valid @RequestBody ChangeObjectOrder changeObjectOrder){
        return columnService.changeColumnOrder(changeObjectOrder);

    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ArrayList<ColumnResponse>> getColumnsFromBoard(@Positive @PathVariable Long boardId){
        return columnService.getColumnsFromBoard(boardId);
    }
}
