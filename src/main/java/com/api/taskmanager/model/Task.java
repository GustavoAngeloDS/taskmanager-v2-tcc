package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "TASKS")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stack_id", nullable = false, updatable = true, insertable = true)
    private Stack stack;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
