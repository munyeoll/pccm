package com.demo.pccm.global.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class ResponseObject<T> {

    private final Integer status;
    private final String message;
    private final T data;

}
