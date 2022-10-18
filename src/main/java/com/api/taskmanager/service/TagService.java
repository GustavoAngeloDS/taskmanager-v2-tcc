package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.Tag;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.repository.TagRepository;
import com.api.taskmanager.response.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;
import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class TagService extends ObjectAuthorizationAbstractService {

    private TagRepository tagRepository;
    private BoardRepository boardRepository;
    private TaskService taskService;

    @Autowired
    public TagService(TagRepository tagRepository, BoardRepository boardRepository, TaskService taskService) {
        this.tagRepository = tagRepository;
        this.boardRepository = boardRepository;
        this.taskService = taskService;
    }

    public List<TagDtoResponse> findAllByBoard(UUID boardId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        return tagRepository.findAllByBoardId(board.getId()).stream().map(TagDtoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TagDtoResponse findById(UUID boardId, UUID tagId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        return TagDtoResponse.fromEntity(tag);
    }

    public TagDtoResponse createTag(UUID boardId, Tag tag, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        Tag newTag = Tag.builder().name(tag.getName()).board(board).build();
        return TagDtoResponse.fromEntity(tagRepository.save(newTag));
    }

    public TagDtoResponse updateTag(UUID boardId, UUID tagId, Tag newTagData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        tag.setName(newTagData.getName());
        return TagDtoResponse.fromEntity(tagRepository.save(tag));
    }

    public void removeTag(UUID boardId, UUID tagId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        taskService.removeTagFromTasks(tag, principal);
        tagRepository.delete(tag);
    }
}
