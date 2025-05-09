package com.wsm.domain.client.entity;

import com.wsm.domain.common.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "CLIENT_SEQ_GENERATOR",
        sequenceName = "CLIENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Data
public class Client extends CommonEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CLIENT_SEQ_GENERATOR"
    )
    private Long clientId;

    @Column(nullable = false, length = 15)
    private String clientNo;

    @Column(nullable = false, length = 30)
    private String clientName;

    @Column(length = 20)
    private String phoneNo;

    @Column(length = 50)
    private String emailAddr;

    @Column(nullable = false, length = 8)
    private String beginYmd;

    @Column(nullable = false, length = 8)
    private String endYmd;

    @Column(length = 1)
    private String deleteYn;

    @Column(length = 300)
    private String note;

    @Column(length = 20)
    private String clientTypeCode;

    @Column
    private LocalDateTime deletedDate;

    @OneToOne(
            mappedBy = "client",
            cascade = CascadeType.PERSIST
    )
    private ClientInfo clientInfo;

    @Builder
    public Client(
            String clientNo, String clientName, String phoneNo, String emailAddr,
            String beginYmd, String endYmd, String note, String clientTypeCode
    ) {
        this.clientNo = clientNo;
        this.clientName = clientName;
        this.phoneNo = phoneNo;
        this.emailAddr = emailAddr;
        this.beginYmd = beginYmd;
        this.endYmd = endYmd;
        this.note = note;
        this.clientTypeCode = clientTypeCode;
        this.deleteYn = "N";
    }

    public void update(
            String clientName, String phoneNo, String emailAddr, String beginYmd,
            String endYmd, String note, String clientTypeCode) {
        this.clientName = clientName;
        this.phoneNo = phoneNo;
        this.emailAddr = emailAddr;
        this.beginYmd = beginYmd;
        this.endYmd = endYmd;
        this.note = note;
        this.clientTypeCode = clientTypeCode;
    }

    public void delete() {
        this.deleteYn = "Y";
        this.deletedDate = LocalDateTime.now();
    }

}
