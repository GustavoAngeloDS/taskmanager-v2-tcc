package com.api.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BOARDS")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "owner_id", updatable = false)
    private User owner;

    @OneToMany(mappedBy = "board")
    private List<Stack> stackList;

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
