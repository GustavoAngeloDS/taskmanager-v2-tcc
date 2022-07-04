package com.api.taskmanager.service;

import com.api.taskmanager.model.Task;
import com.api.taskmanager.repository.TaskRepository;
import com.api.taskmanager.response.TaskDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    public List<TaskDtoResponse> findAll() {
        List<TaskDtoResponse> taskDtoResponseList = new ArrayList<>();
        repository.findAll().forEach(task -> {
            taskDtoResponseList.add(TaskDtoResponse.fromEntity(task));
        });
        return taskDtoResponseList;
    }

    public TaskDtoResponse findById(Long id) {
        Optional<Task> optionalTask = repository.findById(id);
        if (!optionalTask.isPresent())
            return new TaskDtoResponse();

        Task task = optionalTask.get();
        return TaskDtoResponse.fromEntity(task);
    }

    public TaskDtoResponse create(Task task) {
        Task createdTask = repository.save(task);
        return TaskDtoResponse.fromEntity(createdTask);
    }

    public TaskDtoResponse update(Task task) {
        Task updatedTask = repository.save(task);
        return TaskDtoResponse.fromEntity(updatedTask);
    }

    public void remove(Task task) {
        repository.delete(task);
    }
}
