package com.wsm.domain.client.service;

import com.wsm.domain.client.dto.ClientDto;
import com.wsm.domain.client.dto.ClientSaveDto;
import com.wsm.domain.client.entity.Client;
import com.wsm.domain.client.entity.ClientInfoRepository;
import com.wsm.domain.client.entity.ClientRepository;
import com.wsm.domain.common.MyService;
import com.wsm.domain.common.StatusEnum;
import com.wsm.global.exception.BusinessException;
import com.wsm.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@MyService
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientInfoRepository clientInfoRepository;

    /**
     * 신규 고객정보 생성
     * @param clientSaveDto
     * @return Long(New client id)
     */
    public Long save(ClientSaveDto clientSaveDto) {
        if( checkDuplicateClientNo(clientSaveDto.getClientNo()) ){
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        Client client = clientRepository.save(clientSaveDto.toClientEntity());
        clientInfoRepository.save(clientSaveDto.toClientInfoEntity(client));
        return client.getClientId();
    }

    /**
     * 고객정보 수정
     * @param clientSaveDto
     * @return Long(Client id)
     */
    public void update(ClientSaveDto clientSaveDto) {
        Client client = clientRepository.findById(clientSaveDto.getClientId()).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.update(
                clientSaveDto.getClientName(),
                clientSaveDto.getPhoneNo(),
                clientSaveDto.getEmailAddr(),
                clientSaveDto.getBeginYmd(),
                clientSaveDto.getEndYmd(),
                clientSaveDto.getNote()
        );
        client.getClientInfo().update(
                clientSaveDto.getAccessRouteCode()
        );
    }

    /**
     * 고객정보 삭제
     * delete_yn = "Y" 로 업데이트
     * @param clientId
     * @return Long(Client id)
     */
    public void delete(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.delete();
    }

    /**
     * 고객정보 조회
     * @param clientId
     * @return ClientDto
     */
    public ClientDto getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        return new ClientDto(client);
    }

    /**
     * 고객정보 리스트 조회
     * @return List<ClientDto>
     */
    public List<ClientDto> getClientList() {
        List<Client> clientList = clientRepository.findByDeleteYn("N");
        return clientList.stream().map(client -> new ClientDto(client)).collect(Collectors.toList());
    }

    /**
     * 고객번호 중복 확인
     * @param clientNo
     * @return boolean
     */
    public boolean checkDuplicateClientNo(String clientNo) {
        return clientRepository.findByClientNo(clientNo).isPresent();
    }

    /**
     * 고객정보 저장
     * @param clientSaveDtoList
     * @return success count
     */
    public int saveList(List<ClientSaveDto> clientSaveDtoList) {
        clientSaveDtoList.stream().forEach(clientSaveDto -> {
            if(StatusEnum.CREATED.status().equals(clientSaveDto.getStatus())) {
                this.save(clientSaveDto);
            }
            else if(StatusEnum.MODIFIED.status().equals(clientSaveDto.getStatus())) {
                this.update(clientSaveDto);
            }
            else if(StatusEnum.DELETED.status().equals(clientSaveDto.getStatus())) {
                this.delete(clientSaveDto.getClientId());
            }
        });
        return 1;
    }
}
