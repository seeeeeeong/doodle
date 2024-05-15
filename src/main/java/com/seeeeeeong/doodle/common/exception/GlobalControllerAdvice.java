package com.seeeeeeong.doodle.common.exception;

import com.seeeeeeong.doodle.common.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(DoodleApplicationException.class)
    public ResponseEntity<?> applicationHandler(DoodleApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(e.getErrorCode().name()));
    }
}
