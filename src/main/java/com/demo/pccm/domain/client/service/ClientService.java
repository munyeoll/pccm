package com.demo.pccm.domain.client.service;

import com.demo.pccm.domain.client.dto.ClientSaveRequestDto;
import com.demo.pccm.domain.client.entity.Client;
import com.demo.pccm.domain.client.entity.ClientRepository;
import com.demo.pccm.global.exception.BusinessException;
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
            throw new BusinessException("409", "중복된 데이터가 있습니다.");
        }
        Client client = clientRepository.save(requestDto.toEntity());
        return client.getId();
    }

    public boolean checkDuplicateClientNo(String clientNo) {
        return clientRepository.findByClientNo(clientNo).isPresent();
    }
}
