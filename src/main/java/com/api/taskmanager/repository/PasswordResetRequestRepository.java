package com.api.taskmanager.repository;

import com.api.taskmanager.model.PasswordResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, UUID> {
    List<PasswordResetRequest> findByUserEmail(String userEmail);
}
