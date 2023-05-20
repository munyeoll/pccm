package com.demo.pccm.domain.client.api;

import com.demo.pccm.domain.client.dto.ClientSaveDto;
import com.demo.pccm.domain.client.dto.ClientUpdateDto;
import com.demo.pccm.domain.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/client")
@RestController
public class ClientApiController {

    private final ClientService clientService;

    @PostMapping("/save")
    public Long save(@RequestBody ClientSaveDto clientSaveDto) {
        return clientService.save(clientSaveDto);
    }

    @PatchMapping("/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.update(id, clientUpdateDto);
    }

}
