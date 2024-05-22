package com.seeeeeeong.doodle.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "허용하지 않는 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(400, "C003", "엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 오류"),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입의 값입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "접근이 거부됐습니다."),
    ENCRYPTION_ERROR(500, "C007", "암호화에 실패했습니다."),
    DECRYPTION_ERROR(500, "C008", "복호화에 실패했습니다."),
    PARSING_ERROR(500, "C009", "파싱에 실패했습니다."),

    // Security
    AUTHORITY_NOT_FOUND(404, "S001", "유저 권한이 없습니다."),
    INVALID_TOKEN(400, "S002", "유효하지 않은 토큰입니다."),
    JWT_ACCESS_TOKEN_NOT_FOUND(404, "S003", "jwt access token이 없습니다."),
    JWT_REFRESH_TOKEN_NOT_FOUND(404, "S004", "jwt refresh token이 없습니다."),
    EXPIRED_JWT_ACCESS_TOKEN(400, "S005", "jwt access token이 만료되었습니다."),
    EXPIRED_JWT_REFRESH_TOKEN(400, "S006", "jwt refresh token이 만료되었습니다."),
    AUTH_CODE_NOT_FOUND(404, "S007", "authorization header가 비었습니다."),
    JWT_TOKEN_NOT_FOUND(404, "S008", "jwt token이 없습니다."),

    // User
    USER_NOT_FOUND(404, "U001", "회원을 찾을 수 없습니다."),
    DUPLICATED_USER_NAME(400, "U002",  "유저가 중복되었습니다."),
    INVALID_PASSWORD(403,"U003", "비밀번호가 일치하지 않습니다."),
    INVALID_PERMISSION(400, "U004", "권한이 없습니다."),


    // Post
    POST_NOT_FOUND(404, "P001", "포스트를 찾을 수 없습니다."),
    ALREADY_LIKED_POST(404, "P002", "이미 좋아요한 포스트입니다."),

    // Alarm
    ALARM_CONNECT_ERROR(500, "A001", "연결할 수 없습니다."),

    ;


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
