package com.wsm.domain.system.dto;

import com.wsm.domain.system.entity.CodeDetail;
import com.wsm.domain.system.entity.CodeMaster;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodeDetailDto {

    private Long detailId;
    private CodeMaster codeMaster;
    private String value;
    private String label;
    private Integer sortOrder;
    private String useYn;

    public CodeDetailDto(CodeDetail entity) {
        this.detailId = entity.getDetailId();
        this.codeMaster = entity.getCodeMaster();
        this.value = entity.getCodeValue();
        this.label = entity.getCodeLabel();
        this.sortOrder = entity.getSortOrder();
        this.useYn = entity.getUseYn();
    }
}
