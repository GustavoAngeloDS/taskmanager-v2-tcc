package com.api.taskmanager.repository;

import com.api.taskmanager.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findAllByBoardId(UUID boardId);
}
