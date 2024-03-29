package com.api.taskmanager.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailDto {
    private String emailTo;
    private String subject;
    private String text;
}