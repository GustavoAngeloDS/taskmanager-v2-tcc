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

    @Query(value = "SELECT s.name, count(dd) FROM stacks s LEFT JOIN tasks t on t.stack_id = s.id " +
            "LEFT JOIN delivery_date dd on t.delivery_date_id = dd.id " +
            "AND (to_char(dd.date, 'YYYY-MM-DD') < to_char(current_timestamp, 'YYYY-MM-DD') " +
            "OR to_char(dd.date, 'YYYY-MM-DD') = to_char(current_timestamp, 'YYYY-MM-DD') " +
            "AND dd.time < to_char(current_timestamp, 'HH24:MI')) INNER JOIN boards b on s.board_id = b.id " +
            "WHERE b.id = :boardId " +
            "AND dd.accomplished = false " +
            "GROUP BY s.name, s.position " +
            "ORDER BY s.position ASC", nativeQuery = true)
    List<Object[]> findOverdueTasksCount(UUID boardId);
}
