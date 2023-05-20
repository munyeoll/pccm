package com.demo.pccm.domain.client.dto;

import com.demo.pccm.domain.client.entity.Client;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDto {

    private Long id;
    private String clientName;
    private String phoneNo;
    private String emailAddr;
    private String beginYmd;
    private String endYmd;
    private String deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;

    public ClientDto(Client entity) {
        this.id = entity.getId();
        this.clientName = entity.getClientName();
        this.phoneNo = entity.getPhoneNo();
        this.emailAddr = entity.getEmailAddr();
        this.beginYmd = entity.getBeginYmd();
        this.endYmd = entity.getEndYmd();
        this.deleteYn = entity.getDeleteYn();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.deletedDate = entity.getDeletedDate();
    }

}
