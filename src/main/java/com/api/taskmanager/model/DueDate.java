package com.api.taskmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class DueDate extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date date;

    private Boolean isActive;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId", updatable = false, nullable = false)
    private Task task;
}
