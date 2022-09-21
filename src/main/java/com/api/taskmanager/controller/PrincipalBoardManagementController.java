package com.api.taskmanager.controller;

import com.api.taskmanager.model.*;
import com.api.taskmanager.service.BoardService;
import com.api.taskmanager.service.StackService;
import com.api.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("boards")
@PreAuthorize("hasRole('ROLE_USER')")
public class PrincipalBoardManagementController {

    private BoardService boardService;
    private StackService stackService;
    private TaskService taskService;

    @Autowired
    PrincipalBoardManagementController(BoardService boardService, StackService stackService, TaskService taskService) {
        this.boardService = boardService;
        this.stackService = stackService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<?>> findAllBoards(Principal principal) {
        return new ResponseEntity<>(boardService.findAll(principal), HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> findBoardById(@PathVariable(name = "boardId") UUID boardId, Principal principal) {
        return new ResponseEntity<>(boardService.findById(boardId, principal), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody Board board, Principal principal) {
        return new ResponseEntity<>(boardService.create(board, principal), HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable(name = "boardId") UUID boardId,
                                         @RequestBody Board board, Principal principal) {
        return new ResponseEntity<>(boardService.update(boardId, board, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> removeBoard(@PathVariable(name = "boardId") UUID boardId, Principal principal) {
        boardService.remove(boardId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{boardId}/stacks")
    public ResponseEntity<List<?>> findAllStacks(@PathVariable(name = "boardId") UUID boardId, Principal principal) {
        return new ResponseEntity<>(stackService.findAll(boardId, principal), HttpStatus.OK);
    }

    @GetMapping("/{boardId}/stacks/{stackId}")
    public ResponseEntity<?> findStackById(@PathVariable(name = "boardId") UUID boardId,
                                           @PathVariable(name = "stackId") UUID stackId, Principal principal) {
        return new ResponseEntity<>(stackService.findById(boardId, stackId, principal), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/stacks")
    public ResponseEntity<?> createStack(@PathVariable(name = "boardId") UUID boardId,
                                         @RequestBody Stack stack, Principal principal) {
        return new ResponseEntity<>(stackService.create(boardId, stack, principal), HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}/stacks/{stackId}")
    public ResponseEntity<?> updateStack(@PathVariable(name = "boardId") UUID boardId,
                                         @PathVariable(name = "stackId") UUID stackId,
                                         @RequestBody Stack stack, Principal principal) {
        return new ResponseEntity<>(stackService.update(boardId, stackId, stack, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/stacks/{stackId}")
    public ResponseEntity<?> removeStack(@PathVariable(name = "boardId") UUID boardId,
                                         @PathVariable(name = "stackId") UUID stackId, Principal principal) {
        stackService.remove(boardId, stackId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{boardId}/tasks")
    public ResponseEntity<List<?>> findAllTasksByBoard(@PathVariable(name = "boardId") UUID boardId,
                                                       Principal principal) {
        return new ResponseEntity<>(taskService.findAllByBoardId(boardId, principal), HttpStatus.OK);
    }

    @GetMapping("/{boardId}/tasks/{taskId}")
    public ResponseEntity<?> findTaskById(@PathVariable(name = "boardId") UUID boardId,
                                          @PathVariable(name = "taskId") UUID taskId, Principal principal) {
        return new ResponseEntity<>(taskService.findById(boardId, taskId, principal), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/stacks/{stackId}/tasks")
    public ResponseEntity<?> createTask(@PathVariable(name = "boardId") UUID boardId,
                                        @PathVariable(name = "stackId") UUID stackId,
                                        @RequestBody Task task, Principal principal) {
        return new ResponseEntity<>(taskService.create(boardId, stackId, task, principal), HttpStatus.CREATED);
    }

    @PostMapping("/{boardId}/tasks/{taskId}/updateMemberList")
    public ResponseEntity<?> includeTaskMember(@PathVariable(name = "boardId") UUID boardId,
                                               @PathVariable(name = "taskId") UUID taskId,
                                               @RequestBody List<User> taskMembers, Principal principal) {
        return new ResponseEntity<>(taskService.updateTaskMembers(boardId, taskId, taskMembers, principal),
                HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}/tasks/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable(name = "boardId") UUID boardId,
                                        @PathVariable(name = "taskId") UUID taskId,
                                        @RequestBody Task task, Principal principal) {
        return new ResponseEntity<>(taskService.update(boardId, taskId, task, principal), HttpStatus.OK);
    }

    @PutMapping("/{boardId}/tasks/{taskId}/duedate")
    public ResponseEntity<?> updateTaskDueDate(@PathVariable(name = "boardId") UUID boardId,
                                               @PathVariable(name = "taskId") UUID taskId,
                                               @RequestBody DueDate dueDate, Principal principal) {
        return new ResponseEntity<>(taskService.updateTaskDueDate(boardId, taskId, dueDate, principal),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/{boardId}/tasks/{taskId}/includeInternalTask")
    public ResponseEntity<?> includeInternalTask(@PathVariable(name = "boardId") UUID boardId,
                                                 @PathVariable(name = "taskId") UUID taskId,
                                                 @RequestBody InternalTask internalTask, Principal principal) {
        return new ResponseEntity<>(taskService.includeInternalTask(boardId, taskId, internalTask, principal),
                HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}/tasks/{taskId}/internalTask/{internalTaskId}")
    public ResponseEntity<?> updateInternalTask(@PathVariable(name = "boardId") UUID boardId,
                                                 @PathVariable(name = "taskId") UUID taskId,
                                                 @PathVariable(name = "internalTaskId") UUID internalTaskId,
                                                 @RequestBody InternalTask internalTask, Principal principal) {
        return new ResponseEntity<>(taskService.updateInternalTask(boardId, taskId, internalTaskId, internalTask
                , principal), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/tasks/{taskId}/internalTask/{internalTaskId}")
    public ResponseEntity<?> removeInternalTask(@PathVariable(name = "boardId") UUID boardId,
                                                @PathVariable(name = "taskId") UUID taskId,
                                                @PathVariable(name = "internalTaskId") UUID internalTaskId,
                                                Principal principal) {
        taskService.removeInternalTask(boardId, taskId, internalTaskId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{boardId}/tasks/{taskId}/change-stack/{newStackId}")
    public ResponseEntity<?> updateTaskStack(@PathVariable(name = "boardId") UUID boardId,
                                             @PathVariable(name = "taskId") UUID taskId,
                                             @PathVariable(name = "newStackId") UUID newStackId, Principal principal) {
        return new ResponseEntity<>(taskService.updateTaskStack(boardId, taskId, newStackId, principal), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/tasks/{taskId}")
    public ResponseEntity<?> removeTask(@PathVariable(name = "boardId") UUID boardId,
                                        @PathVariable(name = "taskId") UUID taskId, Principal principal) {
        taskService.remove(boardId, taskId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/tasks/{stackId}/removeMember/{memberId}")
    public ResponseEntity<?> removeTaskMember(@PathVariable(name = "boardId") UUID boardId,
                                              @PathVariable(name = "stackId") UUID stackId,
                                              @PathVariable(name = "memberId") UUID memberId, Principal principal) {
        taskService.removeTaskMember(boardId, stackId, memberId, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
