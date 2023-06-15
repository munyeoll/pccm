package com.demo.pccm.domain.client.service;

import com.demo.pccm.domain.client.dto.ClientDto;
import com.demo.pccm.domain.client.dto.ClientSaveDto;
import com.demo.pccm.domain.client.dto.ClientUpdateDto;
import com.demo.pccm.domain.client.entity.Client;
import com.demo.pccm.domain.client.entity.ClientInfoRepository;
import com.demo.pccm.domain.client.entity.ClientRepository;
import com.demo.pccm.domain.common.MyService;
import com.demo.pccm.global.exception.BusinessException;
import com.demo.pccm.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@MyService
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
     * @param clientId
     * @param clientUpdateDto
     * @return Long(Client id)
     */
    public void update(Long clientId, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findById(clientId).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.update(
                clientUpdateDto.getClientName(),
                clientUpdateDto.getPhoneNo(),
                clientUpdateDto.getEmailAddr(),
                clientUpdateDto.getBeginYmd(),
                clientUpdateDto.getEndYmd()
        );
        client.getClientInfo().update(
                clientUpdateDto.getAccessRouteCode()
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
}
