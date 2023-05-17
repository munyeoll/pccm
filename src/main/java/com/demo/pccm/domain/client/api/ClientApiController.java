package com.demo.pccm.domain.client.api;

import com.demo.pccm.domain.client.dto.ClientSaveRequestDto;
import com.demo.pccm.domain.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/client")
@RestController
public class ClientApiController {

    private final ClientService clientService;

    @PostMapping
    public Long save(@RequestBody ClientSaveRequestDto requestDto) {
        return clientService.save(requestDto);
    }

}
