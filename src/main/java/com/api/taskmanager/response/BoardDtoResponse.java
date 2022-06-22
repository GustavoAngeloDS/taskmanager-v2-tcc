package com.api.taskmanager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDtoResponse {
    private Long id;
    private String name;
    private String description;
    private UserDtoResponse owner;
}
