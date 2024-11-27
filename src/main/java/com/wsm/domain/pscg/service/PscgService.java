package com.wsm.domain.pscg.service;

import com.wsm.domain.common.MyService;
import com.wsm.domain.pscg.dto.PscgSaveDto;
import com.wsm.domain.pscg.dto.PscgUpdateDto;
import com.wsm.domain.pscg.entity.Pscg;
import com.wsm.domain.pscg.entity.PscgRepository;
import com.wsm.global.exception.BusinessException;
import com.wsm.global.exception.ErrorCode;
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
     * 상담사정보 수정
     * @param pscgId
     * @param pscgUpdateDto
     */
    public void update(Long pscgId, PscgUpdateDto pscgUpdateDto) {
        Pscg pscg = pscgRepository.findById(pscgId).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        pscg.update(
                pscgUpdateDto.getPscgName(),
                pscgUpdateDto.getPhoneNo()
        );
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
