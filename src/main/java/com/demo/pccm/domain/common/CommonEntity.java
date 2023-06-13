package com.demo.pccm.domain.common;

import java.time.LocalDateTime;

public class CommonEntity {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }
}
