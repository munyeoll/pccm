package com.wsm.domain.system.dto;

import com.wsm.domain.system.entity.CodeMaster;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CodeMasterSaveDto {

    @NotBlank(message = "코드구분은 필수 입력값 입니다.")
    private String codeId;

    @NotBlank(message = "코드구분명은 필수 입력값 입니다.")
    private String codeName;

    private String note;
    private String status;

    public CodeMaster toCodeMasterEntity() {
        return CodeMaster.builder()
                .codeId(codeId)
                .codeName(codeName)
                .note(note)
                .build();
    }
}
