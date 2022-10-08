package com.api.taskmanager.response;

import com.api.taskmanager.model.Stack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StackDtoResponse {
    private UUID id;
    private String name;
    private Date createdOn;
    private Integer position;
    private Set<TaskDtoResponse> taskList;

    public static StackDtoResponse fromEntity(Stack stack) {
        return new StackDtoResponse(stack.getId(), stack.getName(), stack.getCreatedOn(), stack.getPosition(),
                stack.getTaskList().stream().map(TaskDtoResponse::fromEntity).collect(Collectors.toSet()));
    }
}