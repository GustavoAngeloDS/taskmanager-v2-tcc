package com.api.taskmanager.response;

import com.api.taskmanager.enums.NotificationType;
import com.api.taskmanager.model.NotificationConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationConfigurationDtoResponse {
    private UUID id;
    private NotificationType notificationType;
    private String title;
    private String message;

    public static NotificationConfigurationDtoResponse fromEntity(NotificationConfiguration notificationConfiguration) {
        return new NotificationConfigurationDtoResponse(notificationConfiguration.getId(),
                notificationConfiguration.getNotificationType(), notificationConfiguration.getTitle(),
                notificationConfiguration.getMessage());
    }
}
