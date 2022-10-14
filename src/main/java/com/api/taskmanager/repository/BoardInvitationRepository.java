package com.api.taskmanager.repository;

import com.api.taskmanager.model.BoardInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardInvitationRepository extends JpaRepository<BoardInvitation, UUID> {
}
