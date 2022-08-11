package com.api.taskmanager.service;

import com.api.taskmanager.exception.TaskManagerCustomException;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.Stack;
import com.api.taskmanager.model.Task;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.repository.StackRepository;
import com.api.taskmanager.repository.TaskRepository;
import com.api.taskmanager.response.TaskDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.api.taskmanager.exception.TaskManagerCustomException.FORBIDDEN;
import static com.api.taskmanager.exception.TaskManagerCustomException.ID_NOT_FOUND;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private BoardRepository boardRepository;
    private StackRepository stackRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, BoardRepository boardRepository, StackRepository stackRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
        this.stackRepository = stackRepository;
    }

    public List<TaskDtoResponse> findAllByBoardId(UUID boardId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);

        List<TaskDtoResponse> taskDtoResponseList = new ArrayList<>();
        taskRepository.findAllByBoardId(boardId).forEach(task -> {
            taskDtoResponseList.add(TaskDtoResponse.fromEntity(task));
        });
        return taskDtoResponseList;
    }

    public TaskDtoResponse findById(UUID boardId, UUID taskId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        return TaskDtoResponse.fromEntity(task);
    }

    public TaskDtoResponse create(UUID boardId, UUID stackId, Task task, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));


        task.setStack(stack);

        Task createdTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(createdTask);
    }

    public TaskDtoResponse update(UUID boardId, UUID stackId, UUID taskId, Task newTaskData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(stackId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.setDescription(newTaskData.getDescription());
        task.setTitle(newTaskData.getTitle());

        Task updatedTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(updatedTask);
    }

    public TaskDtoResponse updateTaskStack(UUID boardId, UUID taskId, Task newTaskData, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Stack stack = stackRepository.findById(newTaskData.getStack().getId()).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        task.setStack(stack);

        Task updatedTask = taskRepository.save(task);
        return TaskDtoResponse.fromEntity(updatedTask);
    }

    public void remove(UUID boardId, UUID taskId, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));
        if(!hasAccess(board, principal)) throw new TaskManagerCustomException(FORBIDDEN);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskManagerCustomException(ID_NOT_FOUND));

        taskRepository.delete(task);
    }

    private boolean hasAccess(Board board, Principal principal) {
        return (board.getMemberList().stream().filter((member) -> (member.getUsername().equals(principal.getName())))
                .count() > 0 || board.getOwner().getUsername().equals(principal.getName()));
    }
}
