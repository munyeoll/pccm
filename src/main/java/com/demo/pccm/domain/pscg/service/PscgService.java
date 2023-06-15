package com.demo.pccm.domain.pscg.service;

import com.demo.pccm.domain.common.MyService;
import com.demo.pccm.domain.pscg.dto.PscgSaveDto;
import com.demo.pccm.domain.pscg.entity.PscgRepository;
import com.demo.pccm.global.exception.BusinessException;
import com.demo.pccm.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@MyService
public class PscgService {
    private final PscgRepository pscgRepository;

    public Long save(PscgSaveDto pscgSaveDto) {
        if( checkDuplicatePscgNo(pscgSaveDto.getPscgNo()) ){
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        return pscgRepository.save(pscgSaveDto.toEntity()).getPscgId();
    }

    /**
     * 상담사번호 중복 확인
     * @param pscgNo
     * @return boolean
     */
    private boolean checkDuplicatePscgNo(String pscgNo) {
        return pscgRepository.findByPscgNo(pscgNo).isPresent();
    }
}
