package com.wsm.domain.system.dto;

import com.wsm.domain.system.entity.CodeMaster;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodeMasterDto {

    private String codeId;
    private String codeName;
    private String deleteYn;
    private String note;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;

    public CodeMasterDto(CodeMaster entity) {
        this.codeId = entity.getCodeId();
        this.codeName = entity.getCodeName();
        this.deleteYn = entity.getDeleteYn();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.deletedDate = entity.getDeletedDate();
    }
}
