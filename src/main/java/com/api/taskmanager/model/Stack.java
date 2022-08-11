package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "STACKS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @OneToMany(mappedBy = "stack")
    private List<Task> taskList;

    public Stack(String name) {
        this.name = name;
    }
}
