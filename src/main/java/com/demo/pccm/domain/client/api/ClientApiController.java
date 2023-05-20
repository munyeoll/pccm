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

    /*
     * 신규 고객정보 생성 요청
     */
    @PostMapping
    public Long save(@RequestBody ClientSaveDto clientSaveDto) {
        return clientService.save(clientSaveDto);
    }

    /*
     * 고객정보 수정 요청
     */
    @PatchMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.update(id, clientUpdateDto);
    }

    /*
     * 고객정보 삭제 요청
     */
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return clientService.delete(id);
    }

    /*
     * 고객정보 조회 요청
     */
    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    /*
     * 고객정보 리스트 조회 요청
     */
    @GetMapping("/list")
    public List<ClientDto> getClientList() {
        return clientService.getClientList();
    }

}
