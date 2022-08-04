package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.User;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.response.BoardDtoResponse;
import com.api.taskmanager.response.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class BoardService {

    private BoardRepository repository;

    @Autowired
    BoardService(BoardRepository boardRepository) {
        this.repository = boardRepository;
    }

    public List<BoardDtoResponse> findAll() {
        List<BoardDtoResponse> boardDtoResponseList = new ArrayList<>();

        repository.findAll().forEach(board -> {
            boardDtoResponseList.add(new BoardDtoResponse(board.getId(), board.getName(), board.getDescription()));
        });
        return boardDtoResponseList;
    }

    public BoardDtoResponse findById(Long id) {
        Optional<Board> board = repository.findById(id);
        if (!board.isPresent()) throw new TaskManagerCustomException(ID_NOT_FOUND);
        return new BoardDtoResponse(board.get().getId(), board.get().getName(), board.get().getDescription());
    }

    public BoardDtoResponse create(Board board) {
        Board createdBoard = repository.save(board);
        return new BoardDtoResponse(createdBoard.getId(), createdBoard.getName(), createdBoard.getDescription());
    }

    public BoardDtoResponse update(Board board) {
        Board updatedBoard = repository.save(board);

        return new BoardDtoResponse(updatedBoard.getId(), updatedBoard.getName(), updatedBoard.getDescription());
    }

    public void remove(Board board) {
        repository.delete(board);
    }
}
