package com.seeeeeeong.doodle.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoodleApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public DoodleApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {

        if(message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s", "%s", errorCode, getMessage(), message);
    }
}