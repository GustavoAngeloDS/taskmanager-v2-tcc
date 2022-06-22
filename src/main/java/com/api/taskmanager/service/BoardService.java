package com.api.taskmanager.service;

import com.api.taskmanager.model.Board;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.response.BoardDtoResponse;
import com.api.taskmanager.response.UserDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            UserDtoResponse userDtoResponse = new UserDtoResponse(board.getOwner().getId(),
                    board.getOwner().getEmail(), board.getOwner().getUsername(), board.getOwner().getNickName(),
                    board.getOwner().getPhoneNumber());
            boardDtoResponseList.add(new BoardDtoResponse(board.getId(), board.getName(), board.getDescription(),
                    userDtoResponse));
        });
        return boardDtoResponseList;
    }

    public BoardDtoResponse findById(Long id) {
        Optional<Board> board = repository.findById(id);
        if (!board.isPresent())
            return new BoardDtoResponse();

        UserDtoResponse userDtoResponse = new UserDtoResponse(board.get().getOwner().getId(),
                board.get().getOwner().getEmail(), board.get().getOwner().getUsername(),
                board.get().getOwner().getNickName(),
                board.get().getOwner().getPhoneNumber());

        return new BoardDtoResponse(board.get().getId(), board.get().getName(), board.get().getDescription(),
                userDtoResponse);
    }

    public BoardDtoResponse create(Board board) {
        Board createdBoard = repository.save(board);
        UserDtoResponse userDtoResponse = new UserDtoResponse(createdBoard.getOwner().getId(),
                createdBoard.getOwner().getEmail(), createdBoard.getOwner().getUsername(),
                createdBoard.getOwner().getNickName(),
                createdBoard.getOwner().getPhoneNumber());

        return new BoardDtoResponse(createdBoard.getId(), createdBoard.getName(), createdBoard.getDescription(),
                userDtoResponse);
    }

    public BoardDtoResponse update(Board updatedBoard) {
        Board savedBoard = repository.save(updatedBoard);
        UserDtoResponse userDtoResponse = new UserDtoResponse(savedBoard.getOwner().getId(),
                savedBoard.getOwner().getEmail(), savedBoard.getOwner().getUsername(),
                savedBoard.getOwner().getNickName(),
                savedBoard.getOwner().getPhoneNumber());

        return new BoardDtoResponse(savedBoard.getId(), savedBoard.getName(), savedBoard.getDescription(),
                userDtoResponse);
    }

    public void remove(Board board) {
        repository.delete(board);
    }

}
