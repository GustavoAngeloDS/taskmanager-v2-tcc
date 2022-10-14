package com.api.taskmanager.controller;

import com.api.taskmanager.model.BoardInvitation;
import com.api.taskmanager.service.BoardInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("board-invitation")
public class BoardInvitationController {

    private BoardInvitationService boardInvitationService;

    @Autowired
    public BoardInvitationController(BoardInvitationService boardInvitationService) {
        this.boardInvitationService = boardInvitationService;
    }

    @PostMapping("/new-invite")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> newBoardInvitation(@RequestBody BoardInvitation boardInvitation, Principal principal) {
        return new ResponseEntity<>(boardInvitationService.newBoardInvitation(boardInvitation, principal),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/accept-invite/{invitationId}")
    public ResponseEntity<?> acceptInvite(@PathVariable("invitationId") UUID invitationId) {
        boardInvitationService.acceptInvitation(invitationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
