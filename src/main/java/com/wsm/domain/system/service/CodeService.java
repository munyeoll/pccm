package com.wsm.domain.system.service;

import com.wsm.domain.common.MyService;
import com.wsm.domain.system.dto.CodeMasterSaveDto;
import com.wsm.domain.system.entity.CodeDetailRepository;
import com.wsm.domain.system.entity.CodeMaster;
import com.wsm.domain.system.entity.CodeMasterRepository;
import com.wsm.global.exception.BusinessException;
import com.wsm.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@MyService
@Slf4j
public class CodeService {

    private final CodeMasterRepository codeMasterRepository;
    private final CodeDetailRepository codeDetailRepository;

    /**
     * 신규 코드Master 생성
     * @param codeMasterSaveDto
     * @return String(New codeId)
     */
    public String save(CodeMasterSaveDto codeMasterSaveDto) {
        if( checkDuplicateCodeId(codeMasterSaveDto.getCodeId()) ) {
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        CodeMaster codeMaster = codeMasterRepository.save(codeMasterSaveDto.toCodeMasterEntity());
        return codeMaster.getCodeId();
    }

    /**
     * codeId 중복 확인
     * @param codeId
     * @return boolean
     */
    public boolean checkDuplicateCodeId(String codeId) {
        return codeMasterRepository.findByCodeId(codeId).isPresent();
    }
}
