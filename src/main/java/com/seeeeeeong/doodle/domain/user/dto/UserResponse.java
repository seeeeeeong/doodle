package com.seeeeeeong.doodle.domain.user.dto;

import com.seeeeeeong.doodle.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private final Long userId;
    private final String username;

    public static UserResponse of(User user) {
        return new UserResponse(user.getUserId(), user.getUserName());
    }
}
