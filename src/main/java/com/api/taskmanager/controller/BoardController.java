package com.api.taskmanager.controller;

import com.api.taskmanager.model.Board;
import com.api.taskmanager.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("boards")
public class BoardController {

    private BoardService service;

    @Autowired
    BoardController(BoardService boardService) {
        this.service = boardService;
    }

    @GetMapping()
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBoardById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        return new ResponseEntity<>(service.create(board), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateBoard(@RequestBody Board board) {
        return new ResponseEntity<>(service.update(board), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> removeBoard(@RequestBody Board board) {
        service.remove(board);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
