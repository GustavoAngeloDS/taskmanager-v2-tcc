package com.api.taskmanager.model;

import com.api.taskmanager.enums.PasswordResetStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "PASSWORD_RESET_REQUESTS")
public class PasswordResetRequest extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Column(updatable = false, nullable = false)
    private String userEmail;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PasswordResetStatus status;

    public PasswordResetRequest(String userEmail) {
        this.userEmail = userEmail;
    }

    @PrePersist
    void setDefaultValues() {
        this.status = PasswordResetStatus.ACTIVE;
    }
}