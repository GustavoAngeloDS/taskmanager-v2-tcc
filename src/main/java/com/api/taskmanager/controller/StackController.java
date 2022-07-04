package com.api.taskmanager.controller;

import com.api.taskmanager.model.Stack;
import com.api.taskmanager.model.User;
import com.api.taskmanager.service.StackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stacks")
public class StackController {

    private StackService service;

    @Autowired
    StackController(StackService stackService) {
        this.service = stackService;
    }

    @GetMapping()
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findStackById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createStack(@RequestBody Stack stack) {
        return new ResponseEntity<>(service.create(stack), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateStack(@RequestBody Stack stack) {
        return new ResponseEntity<>(service.update(stack), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> removeStack(@RequestBody Stack stack) {
        service.remove(stack);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
