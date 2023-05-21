package com.demo.pccm.domain.client.service;

import com.demo.pccm.domain.client.dto.ClientDto;
import com.demo.pccm.domain.client.dto.ClientUpdateDto;
import com.demo.pccm.domain.client.dto.ClientSaveDto;
import com.demo.pccm.domain.client.entity.Client;
import com.demo.pccm.domain.client.entity.ClientRepository;
import com.demo.pccm.global.exception.BusinessException;
import com.demo.pccm.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * 신규 고객정보 생성
     * @param clientSaveDto
     * @return Long(New client id)
     */
    public Long save(ClientSaveDto clientSaveDto) {
        if( checkDuplicateClientNo(clientSaveDto.getClientNo()) ){
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        Client client = clientRepository.save(clientSaveDto.toEntity());
        return client.getId();
    }

    /**
     * 고객정보 수정
     * @param id
     * @param clientUpdateDto
     * @return Long(Client id)
     */
    public void update(Long id, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.update(
                clientUpdateDto.getClientName(),
                clientUpdateDto.getPhoneNo(),
                clientUpdateDto.getEmailAddr(),
                clientUpdateDto.getBeginYmd(),
                clientUpdateDto.getEndYmd()
        );
    }

    /**
     * 고객정보 삭제
     * delete_yn = "Y" 로 업데이트
     * @param id
     * @return Long(Client id)
     */
    public void delete(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.delete();
    }

    /**
     * 고객정보 조회
     * @param id
     * @return ClientDto
     */
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
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
