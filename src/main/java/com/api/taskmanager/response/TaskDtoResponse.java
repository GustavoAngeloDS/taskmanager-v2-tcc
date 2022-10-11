package com.api.taskmanager.response;

import com.api.taskmanager.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDtoResponse {
    private UUID id;
    private String title;
    private String description;
    private Integer position;
    private Set<UserDtoResponse> memberList;
    private List<InternalTaskDtoResponse> internalTasks;
    private DueDateDtoResponse dueDate;
    private NotificationConfigurationDtoResponse notificationConfiguration;

    public static TaskDtoResponse fromEntity(Task task) {
        return new TaskDtoResponse(task.getId(), task.getTitle(), task.getDescription(), task.getPosition(),
                task.getMemberList().stream().map(UserDtoResponse::fromEntity).collect(Collectors.toSet()),
                task.getInternalTasks().stream().map(InternalTaskDtoResponse::fromEntity)
                        .sorted(Comparator.comparing(InternalTaskDtoResponse::getPosition))
                        .collect(Collectors.toList()), DueDateDtoResponse.fromEntity(task.getDueDate()),
                NotificationConfigurationDtoResponse.fromEntity(task.getNotificationConfiguration()));
    }
}
