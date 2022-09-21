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

    private Boolean active;

    @OneToOne(mappedBy = "dueDate", fetch = FetchType.EAGER)
    private Task task;
}
