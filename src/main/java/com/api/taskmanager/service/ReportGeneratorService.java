package com.api.taskmanager.service;

import com.api.taskmanager.repository.StackRepository;
import com.api.taskmanager.repository.TaskRepository;
import com.api.taskmanager.response.report.OverdueTasksCountFormResponse;
import com.api.taskmanager.response.report.StackTasksCountFormResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReportGeneratorService {

    private StackRepository stackRepository;
    private TaskRepository taskRepository;

    @Autowired
    public ReportGeneratorService(StackRepository stackRepository, TaskRepository taskRepository) {
        this.stackRepository = stackRepository;
        this.taskRepository = taskRepository;
    }

    public List<StackTasksCountFormResponse> stackTasksCount(UUID boardId) {
        List<StackTasksCountFormResponse> stackTasksCountFormResponseList = new ArrayList<>();
        stackRepository.findStackTasksCount(boardId).forEach(obj -> {
            stackTasksCountFormResponseList.add(new StackTasksCountFormResponse(obj[0].toString(),
                    Integer.parseInt(obj[1].toString())));
        });
        return stackTasksCountFormResponseList;
    }

    public List<OverdueTasksCountFormResponse> overDueTasksCount(UUID boardId) {
        List<OverdueTasksCountFormResponse> overdueTasksCountFormResponseList = new ArrayList<>();
        taskRepository.findOverdueTasksCount(boardId).forEach(obj -> {
            overdueTasksCountFormResponseList.add(
                    new OverdueTasksCountFormResponse(obj[0].toString(), Integer.parseInt(obj[1].toString()))
            );
        });

        return overdueTasksCountFormResponseList;
    }
}