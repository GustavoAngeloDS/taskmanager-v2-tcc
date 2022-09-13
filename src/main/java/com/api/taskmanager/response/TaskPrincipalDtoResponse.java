package com.api.taskmanager.response;

import com.api.taskmanager.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskPrincipalDtoResponse {

    private UUID id;
    private String title;
    private String description;
    private Set<UserDtoResponse> memberList;
    private Set<InternalTaskDtoResponse> internalTasks;

    public static TaskPrincipalDtoResponse fromEntity(Task task) {
        return new TaskPrincipalDtoResponse(task.getId(), task.getTitle(), task.getDescription(),
                task.getMemberList().stream().map(UserDtoResponse::fromEntity).collect(Collectors.toSet()),
                task.getInternalTasks().stream().map(InternalTaskDtoResponse::fromEntity).collect(Collectors.toSet()));
    }
}
