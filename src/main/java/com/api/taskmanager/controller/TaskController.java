package com.api.taskmanager.controller;

import com.api.taskmanager.model.Task;
import com.api.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
@PreAuthorize("hasRole('ROLE_USER')")
public class TaskController {

//    private TaskService service;
//
//    @Autowired
//    public TaskController(TaskService taskService) {
//        this.service = taskService;
//    }
//
//    @GetMapping()
//    public ResponseEntity<List<?>> findAll() {
//        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> findTaskById(@PathVariable(name = "id") Long id) {
//        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createTask(@RequestBody Task task) {
//        return new ResponseEntity<>(service.create(task), HttpStatus.CREATED);
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateTask(@RequestBody Task task) {
//        return new ResponseEntity<>(service.update(task), HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<?> removeTask(@RequestBody Task task) {
//        service.remove(task);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
