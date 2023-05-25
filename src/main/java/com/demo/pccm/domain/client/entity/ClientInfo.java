package com.demo.pccm.domain.client.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "CLIENT_DETAIL_SEQ_GENERATOR",
        sequenceName = "CLIENT_DETAIL_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Entity
@Data
public class ClientInfo {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CLIENT_INFO_SEQ_GENERATOR"
    )
    private Long clientInfoId;

    @Column(length = 10)
    private String accessRouteCode;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;

}
