package com.pccm.domain.client.controller;

import com.pccm.domain.client.dto.ClientDto;
import com.pccm.domain.client.dto.ClientSaveDto;
import com.pccm.domain.client.dto.ClientUpdateDto;
import com.pccm.domain.client.service.ClientService;
import com.pccm.global.response.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/client")
@RestController
public class ClientApiController {

    private final ClientService clientService;

    /*
     * 신규 고객정보 생성 요청
     */
    @PostMapping
    public ResponseEntity<ResponseObject<Object>> save(@RequestBody @Valid ClientSaveDto clientSaveDto) {
        Long clientId = clientService.save(clientSaveDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("신규 고객정보 저장이 완료되었습니다.")
                        .data(clientId)
                        .build()
        );
    }

    /*
     * 고객정보 수정 요청
     */
    @PatchMapping("/{client-id}")
    public ResponseEntity<ResponseObject<Object>> update(@PathVariable("client-id") Long clientId, @RequestBody @Valid ClientUpdateDto clientUpdateDto) {
        clientService.update(clientId, clientUpdateDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("고객정보 수정이 완료되었습니다.")
                        .data("")
                        .build()
        );
    }

    /*
     * 고객정보 삭제 요청
     */
    @DeleteMapping("/{client-id}")
    public ResponseEntity<ResponseObject<Object>> delete(@PathVariable("client-id") Long clientId) {
        clientService.delete(clientId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("고객정보 삭제가 완료되었습니다.")
                        .data("")
                        .build()
        );
    }

    /*
     * 고객정보 조회 요청
     */
    @GetMapping("/{client-id}")
    public ResponseEntity<ResponseObject<Object>> getClientById(@PathVariable("client-id") Long clientId) {
        ClientDto client = clientService.getClientById(clientId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("고객정보 조회가 완료되었습니다.")
                        .data(client)
                        .build()
        );
    }

    /*
     * 고객정보 리스트 조회 요청
     */
    @GetMapping("/list")
    public ResponseEntity<ResponseObject<Object>> getClientList() {
        List<ClientDto> clientList = clientService.getClientList();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("고객정보 조회가 완료되었습니다.")
                        .data(clientList)
                        .build()
        );
    }

}
