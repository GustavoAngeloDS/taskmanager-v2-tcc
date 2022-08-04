package com.api.taskmanager.response;

import com.api.taskmanager.model.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDtoResponse {
    private Long id;
    private String name;
    private String description;

    public static BoardDtoResponse fromEntity(Board board) {
        return new BoardDtoResponse(board.getId(), board.getName(), board.getDescription());
    }
}
