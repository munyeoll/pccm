package com.pccm.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;
    private final LocalDateTime timeStamp;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
