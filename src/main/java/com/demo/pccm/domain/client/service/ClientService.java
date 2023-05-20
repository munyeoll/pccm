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

    public Long save(ClientSaveDto clientSaveDto) {
        if( checkDuplicateClientNo(clientSaveDto.getClientNo()) ){
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        Client client = clientRepository.save(clientSaveDto.toEntity());
        return client.getId();
    }

    public Long update(Long id, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.update(
                clientUpdateDto.getClientName(),
                clientUpdateDto.getPhoneNo(),
                clientUpdateDto.getEmailAddr(),
                clientUpdateDto.getBeginYmd(),
                clientUpdateDto.getEndYmd()
        );
        return client.getId();
    }

    public Long delete(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        client.delete();
        return id;
    }

    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.INVALID_PARAMETER));
        return new ClientDto(client);
    }

    public List<ClientDto> getClientList() {
        Optional<Client> clientList = clientRepository.findByDeleteYn("N");
        return clientList.stream().map(client -> new ClientDto(client)).collect(Collectors.toList());
    }

    public boolean checkDuplicateClientNo(String clientNo) {
        return clientRepository.findByClientNo(clientNo).isPresent();
    }
}
