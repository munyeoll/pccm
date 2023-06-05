package com.demo.pccm.domain.client.dto;

import com.demo.pccm.domain.client.entity.Client;
import com.demo.pccm.domain.client.entity.ClientInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ClientSaveDto {

    private String clientNo;
    private String clientName;
    private String phoneNo;
    private String emailAddr;
    private String beginYmd;
    private String endYmd;

    private String accessRouteCode;

    public Client toClientEntity() {
        return Client.builder()
                .clientNo(clientNo)
                .clientName(clientName)
                .phoneNo(phoneNo)
                .emailAddr(emailAddr)
                .beginYmd(beginYmd)
                .endYmd(endYmd)
                .build();
    }

    public ClientInfo toClientInfoEntity(Client client) {
        return ClientInfo.builder()
                .accessRouteCode(accessRouteCode)
                .client(client)
                .build();
    }
}
