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
    private TagDtoResponse tag;
    private Set<UserDtoResponse> memberList;
    private List<InternalTaskDtoResponse> internalTasks;
    private DeliveryDateDtoResponse deliveryDate;
    private NotificationConfigurationDtoResponse notificationConfiguration;

    public static TaskDtoResponse fromEntity(Task task) {
        return new TaskDtoResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPosition(),
                TagDtoResponse.fromEntity(task.getTag()),
                task.getMemberList().stream().map(UserDtoResponse::fromEntity).collect(Collectors.toSet()),
                task.getInternalTasks().stream().map(InternalTaskDtoResponse::fromEntity)
                        .sorted(Comparator.comparing(InternalTaskDtoResponse::getPosition))
                        .collect(Collectors.toList()),
                DeliveryDateDtoResponse.fromEntity(task.getDeliveryDate()),
                NotificationConfigurationDtoResponse.fromEntity(task.getNotificationConfiguration()));
    }
}
