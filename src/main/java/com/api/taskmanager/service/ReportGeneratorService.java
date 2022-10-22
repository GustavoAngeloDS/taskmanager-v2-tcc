package com.api.taskmanager.service;

import com.api.taskmanager.repository.StackRepository;
import com.api.taskmanager.response.report.StackTasksCountFormResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReportGeneratorService {

    private StackRepository stackRepository;

    @Autowired
    public ReportGeneratorService(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    public List<StackTasksCountFormResponse> stackTasksCount(UUID boardId) {
        List<StackTasksCountFormResponse> stackTasksCountFormResponseList = new ArrayList<>();
        stackRepository.findStackTasksCount(boardId).forEach(obj -> {
            stackTasksCountFormResponseList.add(new StackTasksCountFormResponse(obj[0].toString(),
                    Integer.parseInt(obj[1].toString())));
        });
        return stackTasksCountFormResponseList;
    }
}
