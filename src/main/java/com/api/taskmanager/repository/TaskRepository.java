package com.api.taskmanager.repository;

import com.api.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = "SELECT * FROM tasks t " +
            "INNER JOIN stacks s ON (t.stack_id = s.id) " +
            "INNER JOIN boards b ON (s.board_id = b.id) " +
            "WHERE b.id = :boardId", nativeQuery = true)
    List<Task> findAllByBoardId(UUID boardId);

    @Query(value = "SELECT * FROM tasks t " +
            "INNER JOIN stacks s ON (t.stack_id = s.id) " +
            "INNER JOIN boards b ON (s.board_id = b.id) " +
            "WHERE b.id = :boardId and s.id = :stackId", nativeQuery = true)
    List<Task> findAllByBoardIdAndStackId(UUID boardId, UUID stackId);

    @Query(value = "SELECT CASE WHEN COUNT(*) = 0 THEN 0 ELSE MAX(t.position)+1 END FROM tasks t " +
            "INNER JOIN stacks s ON t.stack_id = s.id " +
            "WHERE s.board_id = :boardId AND s.id = :stackId", nativeQuery = true)
    Integer findNextAvailablePositionByBoardAndStack(UUID boardId, UUID stackId);
}
