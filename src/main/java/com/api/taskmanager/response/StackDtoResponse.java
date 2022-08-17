package com.api.taskmanager.response;

import com.api.taskmanager.model.Stack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StackDtoResponse {
    private UUID id;
    private String name;
    private Set<TaskDtoResponse> taskList;

    public static StackDtoResponse fromEntity(Stack stack) {
        return new StackDtoResponse(stack.getId(), stack.getName(), stack.getTaskList().stream().map(TaskDtoResponse::fromEntity).collect(Collectors.toSet()));
    }
}
