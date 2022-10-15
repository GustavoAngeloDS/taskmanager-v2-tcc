package com.api.taskmanager.controller;

import com.api.taskmanager.model.Tag;
import com.api.taskmanager.response.TagDtoResponse;
import com.api.taskmanager.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tags")
@PreAuthorize("hasRole('ROLE_USER')")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/find-all-by-board/{boardId}")
    public ResponseEntity<List<TagDtoResponse>> findAllByBoard(@PathVariable(name = "boardId") UUID boardId,
                                                               Principal principal) {
        return new ResponseEntity<>(tagService.findAllByBoard(boardId, principal), HttpStatus.OK);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagDtoResponse> findById(@PathVariable(name = "tagId") UUID tagId, Principal principal) {
        return new ResponseEntity<>(tagService.findById(tagId, principal), HttpStatus.OK);
    }

    @PostMapping("/board/{boardId}")
    public ResponseEntity<TagDtoResponse> createTag(@PathVariable(name = "boardId") UUID boardId,
                                                    @RequestBody Tag tag, Principal principal) {
        return new ResponseEntity<>(tagService.createTag(boardId, tag, principal), HttpStatus.CREATED);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagDtoResponse> updateTag(@PathVariable(name = "tagId") UUID tagId,
                                                    @RequestBody Tag tag, Principal principal) {
        return new ResponseEntity<>(tagService.updateTag(tagId, tag, principal), HttpStatus.CREATED);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<?> removeTag(@PathVariable(name = "tagId") UUID tagId, Principal principal) {
        tagService.removeTag(tagId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
