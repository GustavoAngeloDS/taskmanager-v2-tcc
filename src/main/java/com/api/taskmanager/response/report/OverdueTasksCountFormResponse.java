package com.api.taskmanager.response.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverdueTasksCountFormResponse {
    private String stackName;
    private Integer tasksCount;
}