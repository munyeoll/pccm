package com.wsm.domain.system.dto;

import com.wsm.domain.system.entity.CodeDetail;
import com.wsm.domain.system.entity.CodeMaster;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CodeDetailSaveDto {

    @NotBlank(message = "코드구분은 필수 입력값 입니다.")
    private String codeId;

    @NotBlank(message = "코드값은 필수 입력값 입니다.")
    private String codeValue;

    @NotBlank(message = "코드명은 필수 입력값 입니다.")
    private String codeLabel;

    private Long detailId;
    private Integer sortOrder;
    private String useYn;
    private String status;

    public CodeDetail toCodeDetailEntity(CodeMaster codeMaster) {
        return CodeDetail.builder()
                .codeMaster(codeMaster)
                .detailId(detailId)
                .codeValue(codeValue)
                .codeLabel(codeLabel)
                .sortOrder(sortOrder)
                .useYn(useYn)
                .build();
    }
}
