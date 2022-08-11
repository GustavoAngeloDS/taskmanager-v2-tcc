package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.Stack;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.repository.StackRepository;
import com.api.taskmanager.response.StackDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;
import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class StackService {
    private StackRepository stackRepository;
    private BoardRepository boardRepository;

    @Autowired
    StackService(StackRepository stackRepository, BoardRepository boardRepository) {
        this.stackRepository = stackRepository;
        this.boardRepository = boardRepository;
    }

    public List<StackDtoResponse> findAll(UUID boardId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        List<StackDtoResponse> stackDtoResponseList = new ArrayList<>();

        stackRepository.findAllByBoardId(boardId).forEach(stack -> {
            stackDtoResponseList.add(StackDtoResponse.fromEntity(stack));
        });
        return stackDtoResponseList;
    }

    public StackDtoResponse findById(UUID boardId, UUID id, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(id).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        return StackDtoResponse.fromEntity(stack);
    }

    public StackDtoResponse create(UUID boardId, Stack stack, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        stack.setBoard(board);
        Stack createdStack = stackRepository.save(stack);

        return StackDtoResponse.fromEntity(createdStack);
    }

    public StackDtoResponse update(UUID boardId, UUID stackId, Stack newStackData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        stack.setName(newStackData.getName());

        Stack updatedStack = stackRepository.save(stack);
        return StackDtoResponse.fromEntity(updatedStack);
    }

    public void remove(UUID boardId, UUID stackId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        stackRepository.delete(stack);
    }

    private boolean hasAccess(Board board, Principal principal) {
        return (board.getMemberList().stream().filter((member) -> (member.getUsername().equals(principal.getName())))
                .count() > 0 || board.getOwner().getUsername().equals(principal.getName()));
    }
}