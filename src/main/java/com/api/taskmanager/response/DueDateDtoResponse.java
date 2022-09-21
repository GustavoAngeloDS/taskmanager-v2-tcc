package com.api.taskmanager.response;

import com.api.taskmanager.model.DueDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DueDateDtoResponse {
    private UUID id;
    private Date date;
    private Boolean active;

    public static DueDateDtoResponse fromEntity(DueDate dueDate){
        return new DueDateDtoResponse(dueDate.getId(), dueDate.getDate(), dueDate.getActive());
    }
}
