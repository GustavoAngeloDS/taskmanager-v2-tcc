package com.api.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String nickName;
    private String phoneNumber;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Board> boards;

    public User(Long id, String email, String username, String nickName, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String username, String nickName, String phoneNumber, List<Board> boards) {
        this.email = email;
        this.username = username;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.boards = boards;
    }
}
