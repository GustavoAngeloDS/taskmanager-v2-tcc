package com.api.taskmanager.model;

import com.api.taskmanager.enums.InvitationStatus;
import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOARD_INVITATIONS")
public class BoardInvitation extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String memberEmail;

    @Setter
    @Column(nullable = false)
    private UUID boardId;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    @PrePersist
    private void setDefaultValues() {
        this.invitationStatus = InvitationStatus.WAITING;
    }
}
