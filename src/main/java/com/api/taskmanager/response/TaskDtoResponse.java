package com.api.taskmanager.response;

import com.api.taskmanager.model.Stack;
import com.api.taskmanager.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDtoResponse {
    private UUID id;
    private String title;
    private String description;
    private StackDtoResponse stack;

    public static TaskDtoResponse fromEntity(Task task) {
        return new TaskDtoResponse(task.getId(), task.getTitle(), task.getDescription(),
                StackDtoResponse.fromEntity(task.getStack()));
    }
}
