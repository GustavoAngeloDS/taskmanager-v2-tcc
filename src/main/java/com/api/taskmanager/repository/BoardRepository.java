package com.api.taskmanager.repository;

import com.api.taskmanager.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
    @Query(value = "SELECT DISTINCT b.* " +
            "FROM boards b " +
            "LEFT JOIN board_users bu on b.id = bu.board_id " +
            "INNER JOIN users u ON u.id = bu.user_id or b.owner_id = u.id " +
            "WHERE u.username = :username",
            nativeQuery = true)
    List<Board> findAllBoardsByUsername(@Param("username") String username);
}