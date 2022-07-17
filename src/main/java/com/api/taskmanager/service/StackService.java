package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Stack;
import com.api.taskmanager.repository.StackRepository;
import com.api.taskmanager.response.StackDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class StackService {
    private StackRepository repository;

    @Autowired
    StackService(StackRepository stackRepository) {
        this.repository = stackRepository;
    }

    public List<StackDtoResponse> findAll() {
        List<StackDtoResponse> stackDtoResponseList = new ArrayList<>();
        repository.findAll().forEach(stack -> {
            stackDtoResponseList.add(StackDtoResponse.fromEntity(stack));
        });
        return stackDtoResponseList;
    }

    public StackDtoResponse findById(Long id) {
        Optional<Stack> optionalStack = repository.findById(id);
        if (!optionalStack.isPresent()) throw new TaskManagerCustomException(ID_NOT_FOUND);

        Stack stack = optionalStack.get();
        return StackDtoResponse.fromEntity(stack);
    }

    public StackDtoResponse create(Stack stack) {
        Stack createdStack = repository.save(stack);
        return StackDtoResponse.fromEntity(createdStack);
    }

    public StackDtoResponse update(Stack stack) {
        Stack updatedStack = repository.save(stack);
        return StackDtoResponse.fromEntity(updatedStack);
    }

    public void remove(Stack stack) {
        repository.delete(stack);
    }
}
