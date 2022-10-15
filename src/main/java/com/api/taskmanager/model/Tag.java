package com.api.taskmanager.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TAGS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @Setter
    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
    private Set<Task> taskList = new HashSet<>();

    @Setter
    private String name;
}
