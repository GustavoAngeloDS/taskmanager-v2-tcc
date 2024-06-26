package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "TASKS")
@Entity
public class Task extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private Integer position;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stack_id", nullable = false)
    private Stack stack;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_users",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> memberList = new HashSet<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<InternalTask> internalTasks = new HashSet<>();

    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deliveryDateId")
    private DeliveryDate deliveryDate;

    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "notificationConfigurationId")
    private NotificationConfiguration notificationConfiguration;
}
