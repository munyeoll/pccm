package com.wsm.domain.pscg.dto;

import com.wsm.domain.pscg.entity.Pscg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PscgSaveDto {

    @NotBlank(message = "상담사번호는 필수 입력값 입니다.")
    private String pscgNo;

    @NotBlank(message = "상담사성명은 필수 입력값 입니다.")
    private String pscgName;

    @NotBlank(message = "연락처는 필수 입력값 입니다.")
    @Pattern(
            message = "연락처 형식이 올바르지 않습니다.",
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$"
    )
    private String phoneNo;

    public Pscg toEntity() {
        return Pscg.builder()
                .pscgNo(pscgNo)
                .pscgName(pscgName)
                .phoneNo(phoneNo)
                .build();
    }
}
