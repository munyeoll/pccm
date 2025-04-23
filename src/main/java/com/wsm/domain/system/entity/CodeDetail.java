package com.wsm.domain.system.entity;

import com.wsm.domain.common.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "CODE_DETAIL_GENERATOR",
        sequenceName = "CODE_DETAIL_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Data
public class CodeDetail extends CommonEntity {

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
    private String codeValue;

    @Column(nullable = false, length = 100)
    private String codeLabel;

    @Column(nullable = false, length = 3)
    private Integer sortOrder;

    @Column(length = 1)
    private String useYn;

    @Builder
    public CodeDetail(
        Long detailId, CodeMaster codeMaster, String codeValue, String codeLabel,
        Integer sortOrder, String useYn
    ) {
        this.detailId = detailId;
        this.codeMaster = codeMaster;
        this.codeValue = codeValue;
        this.codeLabel = codeLabel;
        this.sortOrder = sortOrder;
        this.useYn = StringUtils.hasLength(useYn) ? useYn : "Y";
    }

    public void update(
        String codeValue, String codeLabel, Integer sortOrder, String useYn
    ) {
        this.codeValue = codeValue;
        this.codeLabel = codeLabel;
        this.sortOrder = sortOrder;
        this.useYn = useYn;
    }
}
