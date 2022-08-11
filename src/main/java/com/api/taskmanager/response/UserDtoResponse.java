package com.api.taskmanager.response;

import com.api.taskmanager.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {
    private UUID id;
    private String email;
    private String username;
    private String nickName;
    private String phoneNumber;

    public static UserDtoResponse fromEntity(User user) {
        return new UserDtoResponse(user.getId(), user.getEmail(), user.getUsername(), user.getNickName(),
                user.getPhoneNumber());
    }
}
