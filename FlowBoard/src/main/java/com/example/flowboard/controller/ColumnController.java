package com.example.flowboard.controller;

import com.example.flowboard.model.ChangeObjectOrder;
import com.example.flowboard.model.CreateColumnRequest;
import com.example.flowboard.service.ColumnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/columns")
public class ColumnController {
    ColumnService columnService;

    public ColumnController(ColumnService columnService){
        this.columnService = columnService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createColumn(@RequestBody CreateColumnRequest columnRequest){
        return columnService.createColumn(columnRequest);
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<?> deleteColumn(@PathVariable Long columnId){
        return columnService.deleteColumn(columnId);
    }
    @PatchMapping("/{columnId}")
    public ResponseEntity<?> changeColumnName(@PathVariable Long columnId, @RequestBody String columnName){
        return columnService.changeColumnName(columnId, columnName);
    }

    @PatchMapping("/reorder")
    public ResponseEntity<?> changeColumnOrder(@RequestBody ChangeObjectOrder changeObjectOrder){
        return columnService.changeColumnOrder(changeObjectOrder);

    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getColumnsFromBoard(@PathVariable Long boardId){
        return columnService.getColumnsFromBoard(boardId);
    }

}
