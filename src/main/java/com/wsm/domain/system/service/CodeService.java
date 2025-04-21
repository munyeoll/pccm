package com.wsm.domain.system.service;

import com.wsm.domain.common.MyService;
import com.wsm.domain.common.StatusEnum;
import com.wsm.domain.system.dto.CodeMasterDto;
import com.wsm.domain.system.dto.CodeMasterSaveDto;
import com.wsm.domain.system.entity.CodeDetailRepository;
import com.wsm.domain.system.entity.CodeMaster;
import com.wsm.domain.system.entity.CodeMasterRepository;
import com.wsm.global.exception.BusinessException;
import com.wsm.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@MyService
@Slf4j
public class CodeService {

    private final CodeMasterRepository codeMasterRepository;
    private final CodeDetailRepository codeDetailRepository;

    /**
     * 코드기준정보 생성
     * @param codeMasterSaveDto
     * @return String(New codeId)
     */
    public String insert(CodeMasterSaveDto codeMasterSaveDto) {
        if( checkDuplicateCodeId(codeMasterSaveDto.getCodeId()) ) {
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        CodeMaster codeMaster = codeMasterRepository.save(codeMasterSaveDto.toCodeMasterEntity());
        return codeMaster.getCodeId();
    }

    /**
     * 코드기준정보 수정
     * @param codeMasterSaveDto
     */
    public void update(CodeMasterSaveDto codeMasterSaveDto) {
        CodeMaster codeMaster = codeMasterRepository.findByCodeId(codeMasterSaveDto.getCodeId()).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        codeMaster.update(
                codeMasterSaveDto.getCodeName(),
                codeMasterSaveDto.getNote()
        );
    }

    /**
     * 코드기준정보 삭제
     * @param codeId
     */
    public void delete(String codeId) {
        CodeMaster codeMaster = codeMasterRepository.findByCodeId(codeId).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        codeMaster.delete();
    }

    /**
     * codeId 중복 확인
     * @param codeId
     * @return boolean
     */
    public boolean checkDuplicateCodeId(String codeId) {
        return codeMasterRepository.findByCodeId(codeId).isPresent();
    }

    /**
     * 코드기준정보 목록 조회
     * @return List<CodeMasterDto>
     */
    public List<CodeMasterDto> getCodeMasterList() {
        List<CodeMaster> list = codeMasterRepository.findByDeleteYnOrderByCodeId("N");
        return list.stream().map(codeMaster -> new CodeMasterDto(codeMaster)).collect(Collectors.toList());
    }

    /**
     * 코드기준정보 저장
     * @param codeMasterSaveDtoList
     * @return
     */
    public int save(List<CodeMasterSaveDto> codeMasterSaveDtoList) {
        codeMasterSaveDtoList.stream().forEach(codeMasterSaveDto -> {
            if(StatusEnum.CREATED.status().equals(codeMasterSaveDto.getStatus())) {
                this.insert(codeMasterSaveDto);
            }
            else if(StatusEnum.MODIFIED.status().equals(codeMasterSaveDto.getStatus())) {
                this.update(codeMasterSaveDto);
            }
            else if(StatusEnum.DELETED.status().equals(codeMasterSaveDto.getStatus())) {
                this.delete(codeMasterSaveDto.getCodeId());
            }
        });
        return 1;
    }
}
