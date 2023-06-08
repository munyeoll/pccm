package com.demo.pccm.domain.client.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ClientUpdateDto {

    @NotBlank(message = "성명은 필수 입력값 입니다.")
    private String clientName;

    @NotBlank(message = "연락처는 필수 입력값 입니다.")
    @Pattern(
            message = "연락처 형식이 올바르지 않습니다.",
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$"
    )
    private String phoneNo;

    @Pattern(
            message = "이메일 형식이 올바르지 않습니다.",
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$"
    )
    private String emailAddr;

    @NotBlank(message = "시작일은 필수 입력값 입니다.")
    @Pattern(
            message = "시작일 형식이 올바르지 않습니다.",
            regexp = "^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"
    )
    private String beginYmd;

    @NotBlank(message = "종료일은 필수 입력값 입니다.")
    @Pattern(
            message = "종료일 형식이 올바르지 않습니다.",
            regexp = "^\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$"
    )
    private String endYmd;

    private String accessRouteCode;

}
