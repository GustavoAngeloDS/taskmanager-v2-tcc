package com.api.taskmanager.model;

import com.api.taskmanager.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Setter
    private NotificationType notificationType;

    @Setter
    private String title;

    @Setter
    private String message;

    @OneToOne(mappedBy = "notificationConfiguration", fetch = FetchType.EAGER)
    private Task task;

    @Setter
    private boolean sent;

    public NotificationConfiguration(NotificationType notificationType, String title, String message) {
        this.notificationType = notificationType;
        this.title = title;
        this.message = message;
        this.sent = false;
    }
}