package com.wsm.domain.client.dto;

import com.wsm.domain.client.entity.Client;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDto {

    private Long clientId;
    private String clientNo;
    private String clientName;
    private String phoneNo;
    private String emailAddr;
    private String beginYmd;
    private String endYmd;
    private String deleteYn;
    private String note;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;

    public ClientDto(Client entity) {
        this.clientId = entity.getClientId();
        this.clientNo = entity.getClientNo();
        this.clientName = entity.getClientName();
        this.phoneNo = entity.getPhoneNo();
        this.emailAddr = entity.getEmailAddr();
        this.beginYmd = entity.getBeginYmd();
        this.endYmd = entity.getEndYmd();
        this.deleteYn = entity.getDeleteYn();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.deletedDate = entity.getDeletedDate();
    }

}
