package com.api.taskmanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "BOARDS")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", updatable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "board_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "board_id"))
    private List<User> memberList;

    @OneToMany(mappedBy = "board")
    private List<Stack> stackList;

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
