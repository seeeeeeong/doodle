package com.seeeeeeong.doodle.common.security.jwt.exception;

import com.seeeeeeong.doodle.common.exception.ErrorCode;

public class ExpiredAccessTokenException extends TokenException{

    public ExpiredAccessTokenException() {
        super(ErrorCode.EXPIRED_JWT_ACCESS_TOKEN);
    }
}
