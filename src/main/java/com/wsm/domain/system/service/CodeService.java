package com.wsm.domain.system.service;

import com.wsm.domain.common.MyService;
import com.wsm.domain.common.StatusEnum;
import com.wsm.domain.system.dto.CodeDetailDto;
import com.wsm.domain.system.dto.CodeDetailSaveDto;
import com.wsm.domain.system.dto.CodeMasterDto;
import com.wsm.domain.system.dto.CodeMasterSaveDto;
import com.wsm.domain.system.entity.CodeDetail;
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

    /**********************************************************************
     * 코드기준
     **********************************************************************/
    /**
     * 코드기준정보 생성
     * @param codeMasterSaveDto
     * @return String(New codeId)
     */
    public String insertMaster(CodeMasterSaveDto codeMasterSaveDto) {
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
    public void updateMaster(CodeMasterSaveDto codeMasterSaveDto) {
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
    public void deleteMaster(String codeId) {
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
     * @return int
     */
    public int saveMaster(List<CodeMasterSaveDto> codeMasterSaveDtoList) {
        codeMasterSaveDtoList.stream().forEach(codeMasterSaveDto -> {
            if(StatusEnum.CREATED.status().equals(codeMasterSaveDto.getStatus())) {
                this.insertMaster(codeMasterSaveDto);
            }
            else if(StatusEnum.MODIFIED.status().equals(codeMasterSaveDto.getStatus())) {
                this.updateMaster(codeMasterSaveDto);
            }
            else if(StatusEnum.DELETED.status().equals(codeMasterSaveDto.getStatus())) {
                this.deleteMaster(codeMasterSaveDto.getCodeId());
            }
        });
        return 1;
    }

    /**********************************************************************
     * 코드상세
     **********************************************************************/
    /**
     * 코드상세정보 목록 조회
     * @return List<CodeMasterDto>
     */
    public List<CodeDetailDto> getCodeDetailList() {
        List<CodeDetail> list = codeDetailRepository.findByCodeMaster_CodeIdOrderBySortOrder("TEST");
        return list.stream().map(codeDetail -> new CodeDetailDto(codeDetail)).collect(Collectors.toList());
    }

    /**
     * 코드상세정보 저장
     * @param codeDetailSaveDtoList
     * @return int
     */
    public int saveDetail(List<CodeDetailSaveDto> codeDetailSaveDtoList) {
        codeDetailSaveDtoList.stream().forEach(codeDetailSaveDto -> {
            if(StatusEnum.CREATED.status().equals(codeDetailSaveDto.getStatus())) {
                this.insertDetail(codeDetailSaveDto);
            }
            else if(StatusEnum.MODIFIED.status().equals(codeDetailSaveDto.getStatus())) {
                this.updateDetail(codeDetailSaveDto);
            }
            else if(StatusEnum.DELETED.status().equals(codeDetailSaveDto.getStatus())) {
                this.deleteDetail(codeDetailSaveDto.getDetailId());
            }
        });
        return 1;
    }

    /**
     * 코드상세정보 생성
     * @param codeDetailSaveDto
     * @return Long
     */
    public Long insertDetail(CodeDetailSaveDto codeDetailSaveDto) {
        if( checkDuplicateCodeValue(codeDetailSaveDto.getCodeId(), codeDetailSaveDto.getCodeValue()) ) {
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        CodeMaster codeMaster = CodeMaster.builder().codeId(codeDetailSaveDto.getCodeId()).build(); // 더미 엔티티
        CodeDetail codeDetail = codeDetailRepository.save(codeDetailSaveDto.toCodeDetailEntity(codeMaster));
        return codeDetail.getDetailId();
    }

    /**
     * 코드상세정보 중복 확인
     * @param codeId
     * @param codeValue
     * @return boolean
     */
    public boolean checkDuplicateCodeValue(String codeId, String codeValue) {
        return codeDetailRepository.findByCodeMaster_CodeIdAndCodeValue(codeId, codeValue).isPresent();
    }

    /**
     * 코드상세정보 수정
     * @param codeDetailSaveDto
     */
    public void updateDetail(CodeDetailSaveDto codeDetailSaveDto) {
        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailSaveDto.getDetailId()).orElseThrow(
                () -> new BusinessException(ErrorCode.INVALID_PARAMETER));
        codeDetail.update(
                codeDetailSaveDto.getCodeValue(),
                codeDetailSaveDto.getCodeLabel(),
                codeDetailSaveDto.getSortOrder(),
                codeDetailSaveDto.getUseYn()
        );
    }

    /**
     * 코드상세정보 삭제
     * @param detailId
     */
    public void deleteDetail(Long detailId) {
        codeDetailRepository.deleteById(detailId);
    }
}
