package com.demo.pccm.domain.client.dto;


import com.demo.pccm.domain.client.entity.ClientInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ClientUpdateDto {

    private String clientName;
    private String phoneNo;
    private String emailAddr;
    private String beginYmd;
    private String endYmd;

    private String accessRouteCode;

}
