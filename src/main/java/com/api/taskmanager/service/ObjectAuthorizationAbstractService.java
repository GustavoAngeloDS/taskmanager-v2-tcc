package com.api.taskmanager.service;

import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.Tag;
import com.api.taskmanager.model.User;
import com.api.taskmanager.response.BoardDtoResponse;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public abstract class ObjectAuthorizationAbstractService {

    protected boolean hasAccess(Tag tag, Principal principal) {
        return (tag.getBoard().getMemberList().stream().filter((member) -> (member.getUsername()
                        .equals(principal.getName())))
                .count() > 0 || tag.getBoard().getOwner().getUsername().equals(principal.getName()));
    }

    protected boolean hasAccess(Board board, Principal principal) {
        return (board.getMemberList().stream().filter((member) -> (member.getUsername().equals(principal.getName())))
                .count() > 0 || board.getOwner().getUsername().equals(principal.getName()));
    }

    protected boolean hasAccess(BoardDtoResponse board, Principal principal) {
        return (board.getMemberList().stream().filter((member) -> (member.getUsername().equals(principal.getName())))
                .count() > 0 || board.getOwner().getUsername().equals(principal.getName()));
    }

    protected boolean hasAccess(User user, Principal principal) {
        return principal.getName().equals(user.getUsername());
    }

    protected boolean isOwner(Board board, Principal principal) {
        return board.getOwner().getUsername().equals(principal.getName());
    }
}
