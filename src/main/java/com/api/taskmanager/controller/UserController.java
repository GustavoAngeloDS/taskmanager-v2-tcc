package com.api.taskmanager.controller;

import com.api.taskmanager.model.User;
import com.api.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService service;

    @Autowired
    UserController(UserService userService) {
        this.service = userService;
    }

//    @GetMapping()
//    public ResponseEntity<List<?>> findAll() {
//        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> findUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(service.update(user), HttpStatus.OK);
    }

//    @DeleteMapping
//    public ResponseEntity<?> removeUser(@RequestBody User user) {
//        service.remove(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
