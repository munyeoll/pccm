package com.demo.pccm.domain.client.api;

import com.demo.pccm.domain.client.dto.ClientDto;
import com.demo.pccm.domain.client.dto.ClientSaveDto;
import com.demo.pccm.domain.client.dto.ClientUpdateDto;
import com.demo.pccm.domain.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/client")
@RestController
public class ClientApiController {

    private final ClientService clientService;

    @PostMapping
    public Long save(@RequestBody ClientSaveDto clientSaveDto) {
        return clientService.save(clientSaveDto);
    }

    @PatchMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.update(id, clientUpdateDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return clientService.delete(id);
    }

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/list")
    public List<ClientDto> getClientList() {
        return clientService.getClientList();
    }

}
