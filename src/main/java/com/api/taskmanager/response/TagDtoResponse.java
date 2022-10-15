package com.api.taskmanager.response;

import com.api.taskmanager.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagDtoResponse {
    private UUID id;
    private String name;

    public static TagDtoResponse fromEntity(Tag tag) {
        if(Objects.isNull(tag)) return new TagDtoResponse();
        return new TagDtoResponse(tag.getId(), tag.getName());
    }
}
