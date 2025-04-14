package com.wsm.domain.system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SequenceGenerator(
        name = "CODE_DETAIL_GENERATOR",
        sequenceName = "CODE_DETAIL_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Data
@Builder
public class CodeDetail {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CODE_DETAIL_SEQ"
    )
    private Long detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id", nullable = false)
    private CodeMaster codeMaster;

    @Column(nullable = false, length = 50)
    private String value;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false, length = 3)
    private Integer sortOrder;

    @Column(length = 1)
    private String useYn;
}
