package com.api.taskmanager.repository;

import com.api.taskmanager.model.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StackRepository extends JpaRepository<Stack, UUID> {
    List<Stack> findAllByBoardId(UUID boardId);

    @Query(value = "SELECT COALESCE(MAX(s.position), 0)+1 FROM stacks s WHERE s.board_id = :boardId",
            nativeQuery = true)
    Integer findNextAvailablePositionByBoardId(UUID boardId);
}
