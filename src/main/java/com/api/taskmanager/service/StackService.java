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
import java.util.*;
import java.util.stream.Collectors;

import static com.api.taskmanager.exception.TaskManagerCustomException.*;

@Service
public class StackService extends ObjectAuthorizationAbstractService {
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

        Collections.sort(stackDtoResponseList, Comparator.comparing(StackDtoResponse::getPosition));
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
        stack.setPosition(stackRepository.findNextAvailablePositionByBoardId(boardId));
        Stack createdStack = stackRepository.save(stack);

        return StackDtoResponse.fromEntity(createdStack);
    }

    public List<StackDtoResponse> updateStackPosition(UUID boardId, UUID stackToBeUpdatedId,
                                                      Integer newPosition, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack originalStack = stackRepository.findById(stackToBeUpdatedId)
                .orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        if(newPosition != originalStack.getPosition()) {
            stackRepository.findAllByBoardId(boardId).stream()
                    .filter(filteredStack -> filteredStack.getPosition() == newPosition).findFirst().ifPresentOrElse(
                            stack -> {
                                stack.setPosition(originalStack.getPosition());
                                stackRepository.save(stack);
                            }, () -> new TaskManagerCustomException(POSITION_IS_NOT_AVAILABLE)
                    );

            originalStack.setPosition(newPosition);
            stackRepository.save(originalStack);
        }

        List<StackDtoResponse> stackDtoResponseList = stackRepository.findAllByBoardId(boardId).stream()
                .map(StackDtoResponse::fromEntity).collect(Collectors.toList());

        Collections.sort(stackDtoResponseList, Comparator.comparing(StackDtoResponse::getPosition));
        return stackDtoResponseList;
    }

    public StackDtoResponse update(UUID boardId, UUID stackId, Stack newStackData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        stack.setName(newStackData.getName());

        Stack updatedStack = stackRepository.save(stack);
        return StackDtoResponse.fromEntity(updatedStack);
    }

    public StackDtoResponse remove(UUID boardId, UUID stackId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        if(stack.getTaskList().isEmpty()) {
            stackRepository.delete(stack);
            return null;
        } else {
            return StackDtoResponse.fromEntity(stack);
        }
    }
}