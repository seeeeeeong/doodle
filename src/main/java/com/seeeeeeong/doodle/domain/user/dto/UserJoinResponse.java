package com.seeeeeeong.doodle.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserJoinResponse {

    private final Long id;
    private final String userName;

}
