package com.demo.pccm.domain.pscg.entity;

import com.demo.pccm.domain.common.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@SequenceGenerator(
        name = "PSCG_SEQ_GENERATOR",
        sequenceName = "PSCG_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Entity
@Data
public class Pscg extends CommonEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PSCG_SEQ_GENERATOR"
    )
    private Long pscgId;

    @Column(nullable = false, length = 15)
    private String pscgNo;

    @Column(nullable = false, length = 30)
    private String pscgName;

    @Column(length = 20)
    private String phoneNo;

    @Column
    private String deleteYn;

    private LocalDateTime deletedDate;

    @Builder
    public Pscg(String pscgNo, String pscgName, String phoneNo) {
        this.pscgNo = pscgNo;
        this.pscgName = pscgName;
        this.phoneNo = phoneNo;
        this.deleteYn = "N";
        setCreatedDate();
    }

    public void update(String pscgName, String phoneNo) {
        this.pscgName = pscgName;
        setModifiedDate();
    }

    public void delete() {
        this.deleteYn = "Y";
        this.deletedDate = LocalDateTime.now();
    }
}
