package com.api.taskmanager.response;

import com.api.taskmanager.model.InternalTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InternalTaskDtoResponse {

    private UUID id;
    private Boolean checked;
    private String description;
    private Integer position;

    public static InternalTaskDtoResponse fromEntity(InternalTask internalTask) {
        return new InternalTaskDtoResponse(internalTask.getId(), internalTask.getChecked(),
                internalTask.getDescription(), internalTask.getPosition());
    }
}
