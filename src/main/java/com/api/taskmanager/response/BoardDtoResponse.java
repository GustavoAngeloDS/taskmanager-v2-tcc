package com.api.taskmanager.response;

import com.api.taskmanager.model.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardDtoResponse {
    private UUID id;
    private String name;
    private String description;
    private Set<UserDtoResponse> memberList;
    private UserDtoResponse owner;

    public static BoardDtoResponse fromEntity(Board board) {
        return new BoardDtoResponse(board.getId(), board.getName(), board.getDescription(),
                board.getMemberList().stream().map(UserDtoResponse::fromEntity).collect(Collectors.toSet()),
                UserDtoResponse.fromEntity(board.getOwner()));
    }
}
