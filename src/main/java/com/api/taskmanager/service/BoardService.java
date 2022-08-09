package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.response.BoardDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;
import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class BoardService {

    private BoardRepository repository;

    @Autowired
    BoardService(BoardRepository boardRepository) {
        this.repository = boardRepository;
    }

    public List<BoardDtoResponse> findAll(Principal principal) {
        List<BoardDtoResponse> boardDtoResponseList = new ArrayList<>();

        repository.findAllBoardsByOwnerUsername(principal.getName()).forEach(board -> {
            boardDtoResponseList.add(new BoardDtoResponse(board.getId(), board.getName(), board.getDescription()));
        });
        return boardDtoResponseList;
    }

    public BoardDtoResponse findById(Long id, Principal principal) {
        Board board = repository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        return new BoardDtoResponse(board.getId(), board.getName(), board.getDescription());
    }

    public BoardDtoResponse create(Board board, Principal principal) {
        Board createdBoard = repository.save(board);
        return new BoardDtoResponse(createdBoard.getId(), createdBoard.getName(), createdBoard.getDescription());
    }

    public BoardDtoResponse update(Long boardId, Principal principal) {
        Board board = repository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        Board updatedBoard = repository.save(board);
        return new BoardDtoResponse(updatedBoard.getId(), updatedBoard.getName(), updatedBoard.getDescription());
    }

    public void remove(Long boardId, Principal principal) {
        Board board = repository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        repository.delete(board);
    }

    private boolean hasAccess(Board board, Principal principal) {
        return principal.getName().equals(board.getOwner().getUsername());
    }
}
