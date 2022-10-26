package com.api.taskmanager.controller.report;

import com.api.taskmanager.service.ReportGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("reports")
@PreAuthorize("hasRole('ROLE_USER')")
public class ReportGeneratorController {

    private ReportGeneratorService reportGeneratorService;

    @Autowired
    public ReportGeneratorController(ReportGeneratorService reportGeneratorService) {
        this.reportGeneratorService = reportGeneratorService;
    }

    @GetMapping("/boardId/{boardId}/find-stack-tasks-count")
    public ResponseEntity<List<?>> findStackTasksCount(@PathVariable(name = "boardId") UUID boardId) {
        return new ResponseEntity<>(reportGeneratorService.stackTasksCount(boardId), HttpStatus.OK);
    }

    @GetMapping("/boardId/{boardId}/find-overdue-tasks-count")
    public ResponseEntity<List<?>> findOverdueTasks(@PathVariable(name = "boardId") UUID boardId) {
        return new ResponseEntity<>(reportGeneratorService.overDueTasksCount(boardId), HttpStatus.OK);
    }
}
