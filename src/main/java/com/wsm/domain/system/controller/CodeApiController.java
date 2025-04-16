package com.wsm.domain.system.controller;

import com.wsm.domain.system.dto.CodeMasterSaveDto;
import com.wsm.domain.system.service.CodeService;
import com.wsm.global.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/code")
@RestController
@Slf4j
public class CodeApiController {

    private final CodeService codeService;

    /*
     * 신규 코드정보 생성 요청
     */
    @PostMapping
    public ResponseEntity<ResponseObject<Object>> save(@RequestBody @Valid CodeMasterSaveDto codeMasterSaveDto) {
        String codeId = codeService.save(codeMasterSaveDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("신규 코드정보 저장이 완료되었습니다.")
                        .data(codeId)
                        .build()
        );
    }
}
