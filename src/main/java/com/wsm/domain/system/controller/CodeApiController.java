package com.wsm.domain.system.controller;

import com.wsm.domain.system.dto.CodeDetailDto;
import com.wsm.domain.system.dto.CodeDetailSaveDto;
import com.wsm.domain.system.dto.CodeMasterDto;
import com.wsm.domain.system.dto.CodeMasterSaveDto;
import com.wsm.domain.system.service.CodeService;
import com.wsm.global.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/code")
@RestController
@Slf4j
public class CodeApiController {

    private final CodeService codeService;

    /**********************************************************************
     * 코드기준정보
     **********************************************************************/
    /**
     * 코드기준정보 생성
     * @param codeMasterSaveDto
     * @return ResponseEntity
     */
    @PostMapping("/master")
    public ResponseEntity<ResponseObject<Object>> saveCodeMaster(@RequestBody @Valid CodeMasterSaveDto codeMasterSaveDto) {
        String codeId = codeService.insertMaster(codeMasterSaveDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("신규 코드기준정보 저장이 완료되었습니다.")
                        .data(codeId)
                        .build()
        );
    }

    /**
     * 코드기준정보 저장
     * @param codeMasterSaveDtoList
     * @return ResponseEntity
     */
    @PostMapping("/master/save")
    public ResponseEntity<ResponseObject<Object>> saveCodeMasterList(@RequestBody @Valid List<CodeMasterSaveDto> codeMasterSaveDtoList) {
        codeService.saveMaster(codeMasterSaveDtoList);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("코드기준정보 저장이 완료되었습니다.")
                        .data("")
                        .build()
        );
    }

    /**
     * 코드기준정보 목록 조회
     * @returnm ResponseEntity
     */
    @GetMapping("/master/list")
    public ResponseEntity<ResponseObject<Object>> getCodeMasterList() {
        List<CodeMasterDto> codeMasterList = codeService.getCodeMasterList();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("코드기준정보 조회가 완료되었습니다.")
                        .data(codeMasterList)
                        .build()
        );
    }

    /**********************************************************************
     * 코드상세
     **********************************************************************/
    /**
     * 코드상세정보 목록 조회
     * @return ResponseEntity
     */
    @GetMapping("/detail/list")
    public ResponseEntity<ResponseObject<Object>> getCodeDetailList() {
        List<CodeDetailDto> codeDetailList = codeService.getCodeDetailList();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("코드상세정보 조회가 완료되었습니다.")
                        .data(codeDetailList)
                        .build()
        );
    }

    /**
     * 코드상세정보 저장
     * @param CodeDetailSaveDto
     * @return ResponseEntity
     */
    @PostMapping("/detail/save")
    public ResponseEntity<ResponseObject<Object>> saveCodeDetailList(@RequestBody @Valid List<CodeDetailSaveDto> CodeDetailSaveDto) {
        codeService.saveDetail(CodeDetailSaveDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("코드기준정보 저장이 완료되었습니다.")
                        .data("")
                        .build()
        );
    }
}
