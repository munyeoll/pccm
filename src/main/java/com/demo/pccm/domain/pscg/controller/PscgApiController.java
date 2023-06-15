package com.demo.pccm.domain.pscg.controller;

import com.demo.pccm.domain.pscg.dto.PscgSaveDto;
import com.demo.pccm.domain.pscg.service.PscgService;
import com.demo.pccm.global.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/pscg")
@RestController
public class PscgApiController {
    private final PscgService pscgService;

    @PostMapping
    public ResponseEntity<ResponseObject<Object>> save(@RequestBody @Valid PscgSaveDto pscgSaveDto) {
        Long pscgId = pscgService.save(pscgSaveDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("신규 상담사정보 저장이 완료되었습니다.")
                        .data(pscgId)
                        .build()
        );
    }

}
