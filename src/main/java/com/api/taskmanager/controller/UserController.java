package com.api.taskmanager.controller;

import com.api.taskmanager.model.NewPassword;
import com.api.taskmanager.model.PasswordResetRequest;
import com.api.taskmanager.model.User;
import com.api.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService service;

    @Autowired
    UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> findUserById(@PathVariable(name = "id") UUID id, Principal principal) {
        return new ResponseEntity<>(service.findById(id, principal), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/validateLogin")
    public ResponseEntity<?> validateLogin(Principal principal) {
        return new ResponseEntity<>(service.authenticate(principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable(name = "id") UUID id, Principal principal) {
        return new ResponseEntity<>(service.update(id, user, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> removeUser(@PathVariable(name = "id") UUID id, Principal principal) {
        service.remove(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> createPasswordResetRequest(@RequestBody PasswordResetRequest passwordResetRequest) {
        service.createPasswordResetRequest(passwordResetRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/reset-password/{passwordResetSolicitationId}")
    public ResponseEntity<?> updateUserPassword(@PathVariable(name = "passwordResetSolicitationId") UUID id,
                                                @RequestBody NewPassword newPassword) {
        service.updatePassword(id, newPassword);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}