package com.api.taskmanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "INTERNAL_TASKS")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InternalTask extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private Boolean checked;

    @Setter
    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId", updatable = false, nullable = false)
    private Task task;

    public InternalTask(Boolean checked, String description, Task task) {
        this.checked = checked;
        this.description = description;
        this.task = task;
    }
}