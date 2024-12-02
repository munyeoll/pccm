package com.wsm.domain.common;

public enum StatusEnum {
    CREATED("C"),
    MODIFIED("M"),
    DELETED("D")
    ;

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String status() {
        return this.status;
    }
}
