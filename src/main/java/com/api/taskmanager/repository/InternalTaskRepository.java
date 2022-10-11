package com.api.taskmanager.repository;

import com.api.taskmanager.model.InternalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface InternalTaskRepository extends JpaRepository<InternalTask, UUID> {
    @Query(value = "SELECT CASE WHEN COUNT(*) = 0 THEN 0 ELSE MAX(it.position)+1 END " +
            "FROM internal_tasks it WHERE it.task_id = :taskId", nativeQuery = true)
    Integer findNextAvailablePositionByTask(UUID taskId);
}
