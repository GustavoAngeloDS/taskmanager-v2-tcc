package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "STACKS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stack extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer position;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @OneToMany(mappedBy = "stack", cascade = CascadeType.REMOVE)
    private Set<Task> taskList = new HashSet<>();
}
