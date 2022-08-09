package com.api.taskmanager.controller;

import com.api.taskmanager.model.Board;
import com.api.taskmanager.service.BoardService;
import com.api.taskmanager.service.StackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("boards")
@PreAuthorize("hasRole('ROLE_USER')")
public class BoardController {

    private BoardService boardService;
    private StackService stackService;

    @Autowired
    BoardController(BoardService boardService, StackService stackService) {
        this.boardService = boardService;
        this.stackService = stackService;
    }

    @GetMapping
    public ResponseEntity<List<?>> findAllBoards(Principal principal) {
        return new ResponseEntity<>(boardService.findAll(principal), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBoardById(@PathVariable(name = "id") Long id, Principal principal) {
        return new ResponseEntity<>(boardService.findById(id, principal), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody Board board, Principal principal) {
        return new ResponseEntity<>(boardService.create(board, principal), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable(name = "id") Long id, Principal principal) {
        return new ResponseEntity<>(boardService.update(id, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeBoard(@PathVariable(name = "id") Long id, Principal principal) {
        boardService.remove(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }










//
//
//
//
//    @GetMapping
//    public ResponseEntity<List<?>> findAllStacks(Principal principal) {
//        return new ResponseEntity<>(stackService.findAll(principal), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> findStackById(@PathVariable(name = "id") Long id, Principal principal) {
//        return new ResponseEntity<>(stackService.findById(id, principal), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createStack(@RequestBody Board board, Principal principal) {
//        return new ResponseEntity<>(stackService.create(board, principal), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateStack(@PathVariable(name = "id") Long id, Principal principal) {
//        return new ResponseEntity<>(stackService.update(id, principal), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> removeStack(@PathVariable(name = "id") Long id, Principal principal) {
//        stackService.remove(id, principal);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
