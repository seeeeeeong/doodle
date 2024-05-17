package com.seeeeeeong.doodle.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseJwtToken {

    private final String accessToken;
    private final String refreshToken;

    public static ResponseJwtToken of(String accessToken, String refreshToken) {
        return new ResponseJwtToken(accessToken, refreshToken);
    }
}
