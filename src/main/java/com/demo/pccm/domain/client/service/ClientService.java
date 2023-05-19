package com.demo.pccm.domain.client.service;

import com.demo.pccm.domain.client.dto.ClientSaveRequestDto;
import com.demo.pccm.domain.client.entity.Client;
import com.demo.pccm.domain.client.entity.ClientRepository;
import com.demo.pccm.global.exception.BusinessException;
import com.demo.pccm.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public Long save(ClientSaveRequestDto requestDto) {
        if( checkDuplicateClientNo(requestDto.getClientNo()) ){
            throw new BusinessException(ErrorCode.CONFLICT);
        }
        Client client = clientRepository.save(requestDto.toEntity());
        return client.getId();
    }

    public boolean checkDuplicateClientNo(String clientNo) {
        return clientRepository.findByClientNo(clientNo).isPresent();
    }
}
