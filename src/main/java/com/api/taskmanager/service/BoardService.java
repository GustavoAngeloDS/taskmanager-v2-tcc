package com.api.taskmanager.service;

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
            User owner = board.getOwner();
            UserDtoResponse userDtoResponse = UserDtoResponse.builder()
                    .id(owner.getId())
                    .email(owner.getEmail())
                    .username(owner.getUsername())
                    .nickName(owner.getNickName())
                    .phoneNumber(owner.getPhoneNumber()).build();

            boardDtoResponseList.add(new BoardDtoResponse(board.getId(), board.getName(), board.getDescription(),
                    userDtoResponse));
        });
        return boardDtoResponseList;
    }

    public BoardDtoResponse findById(Long id) {
        Optional<Board> board = repository.findById(id);
        if (!board.isPresent())
            return new BoardDtoResponse();

        User owner = board.get().getOwner();
        UserDtoResponse userDtoResponse = UserDtoResponse.builder()
                .id(owner.getId())
                .email(owner.getEmail())
                .username(owner.getUsername())
                .nickName(owner.getNickName())
                .phoneNumber(owner.getPhoneNumber()).build();

        return new BoardDtoResponse(board.get().getId(), board.get().getName(), board.get().getDescription(),
                userDtoResponse);
    }

    public BoardDtoResponse create(Board board) {
        Board createdBoard = repository.save(board);
        User owner = createdBoard.getOwner();

        UserDtoResponse userDtoResponse = UserDtoResponse.builder()
                .id(owner.getId())
                .email(owner.getEmail())
                .username(owner.getUsername())
                .nickName(owner.getNickName())
                .phoneNumber(owner.getPhoneNumber()).build();

        return new BoardDtoResponse(createdBoard.getId(), createdBoard.getName(), createdBoard.getDescription(),
                userDtoResponse);
    }

    public BoardDtoResponse update(Board board) {
        Board updatedBoard = repository.save(board);
        User owner = updatedBoard.getOwner();

        UserDtoResponse userDtoResponse = UserDtoResponse.builder()
                .id(owner.getId())
                .email(owner.getEmail())
                .username(owner.getUsername())
                .nickName(owner.getNickName())
                .phoneNumber(owner.getPhoneNumber()).build();

        return new BoardDtoResponse(updatedBoard.getId(), updatedBoard.getName(), updatedBoard.getDescription(),
                userDtoResponse);
    }

    public void remove(Board board) {
        repository.delete(board);
    }
}
