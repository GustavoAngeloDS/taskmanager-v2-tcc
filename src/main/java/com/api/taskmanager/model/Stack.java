package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "STACKS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @OneToMany(mappedBy = "stack")
    private List<Task> taskList;

    public Stack(String name) {
        this.name = name;
    }
}
