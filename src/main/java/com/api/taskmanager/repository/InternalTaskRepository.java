package com.api.taskmanager.repository;

import com.api.taskmanager.model.InternalTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InternalTaskRepository extends JpaRepository<InternalTask, UUID> {
}
