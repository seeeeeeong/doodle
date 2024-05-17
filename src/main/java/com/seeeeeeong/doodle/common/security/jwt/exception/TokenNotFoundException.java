package com.seeeeeeong.doodle.common.security.jwt.exception;

import com.seeeeeeong.doodle.common.exception.ErrorCode;

public class TokenNotFoundException extends TokenException{

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenNotFoundException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
