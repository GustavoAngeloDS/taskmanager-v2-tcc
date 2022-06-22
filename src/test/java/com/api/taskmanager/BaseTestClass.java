package com.api.taskmanager;

import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.User;
import com.api.taskmanager.response.BoardDtoResponse;
import com.api.taskmanager.response.UserDtoResponse;

import java.util.List;

public abstract class BaseTestClass {

    protected final UserDtoResponse defaultMockUserDtoResponse = new UserDtoResponse(1L, "teste123@teste.com", "Test", "myMock", "47554");
    protected final List<BoardDtoResponse> defaultMockBoardDtoResponseList = List.of(new BoardDtoResponse(1L, "Quadro mock", "Quadro teste", defaultMockUserDtoResponse));

    protected final User defaultMockUser = new User(1L, "teste123@teste.com", "Test", "myMock", "47554");
    protected final List<Board> defaultBoardList = List.of(new Board(1L, "Quadro mock", "Quadro teste", defaultMockUser));

}
