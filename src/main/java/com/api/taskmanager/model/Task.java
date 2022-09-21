package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stack_id", nullable = false, updatable = true, insertable = true)
    private Stack stack;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_users",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> memberList = new HashSet<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InternalTask> internalTasks = new HashSet<>();

    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "dueDateId", nullable = true)
    private DueDate dueDate;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
