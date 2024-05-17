package com.seeeeeeong.doodle.common.security.jwt.exception;

import com.seeeeeeong.doodle.common.exception.ErrorCode;

public class ExpiredRefreshTokenException extends TokenException{

    public ExpiredRefreshTokenException() {
        super(ErrorCode.EXPIRED_JWT_REFRESH_TOKEN);
    }

}
