package com.api.taskmanager.response;

import com.api.taskmanager.model.Stack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StackDtoResponse {
    private Long id;
    private String name;
    private BoardDtoResponse board;

    public static StackDtoResponse fromEntity(Stack stack) {
        return new StackDtoResponse(stack.getId(), stack.getName(),
                BoardDtoResponse.fromEntity(stack.getBoard()));
    }
}
