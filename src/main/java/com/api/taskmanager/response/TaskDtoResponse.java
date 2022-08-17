package com.api.taskmanager.response;

import com.api.taskmanager.model.Task;
import com.api.taskmanager.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDtoResponse {
    private UUID id;
    private String title;
    private String description;
    private Set<UserDtoResponse> memberList;

    public static TaskDtoResponse fromEntity(Task task) {
        return new TaskDtoResponse(task.getId(), task.getTitle(), task.getDescription(),
                task.getMemberList().stream().map(UserDtoResponse::fromEntity).collect(Collectors.toSet()));
    }
}
