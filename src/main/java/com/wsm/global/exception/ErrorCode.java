package com.wsm.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "요청 정보를 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "허용되지 않은 메서드입니다."),

    /*
     * 409 중복된 데이터
     */
    CONFLICT(HttpStatus.CONFLICT.value(), "중복된 데이터가 존재합니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 서버 오류입니다."),

    /*
     * 400 잘못된 요청 정보
     */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), "요청 정보가 잘못되었습니다.")
    ;

    private final int status;
    private final String message;

}
