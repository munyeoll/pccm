package com.demo.pccm.domain.client.controller;

import com.demo.pccm.domain.client.dto.ClientDto;
import com.demo.pccm.domain.client.dto.ClientSaveDto;
import com.demo.pccm.domain.client.dto.ClientUpdateDto;
import com.demo.pccm.domain.client.service.ClientService;
import com.demo.pccm.global.response.ResponseObject;
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
    public ResponseEntity<ResponseObject<Object>> save(@RequestBody ClientSaveDto clientSaveDto) {
        Long id = clientService.save(clientSaveDto);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message("신규 고객정보 저장이 완료되었습니다.")
                        .data(id)
                        .build()
        );
    }

    /*
     * 고객정보 수정 요청
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject<Object>> update(@PathVariable Long id, @RequestBody ClientUpdateDto clientUpdateDto) {
        clientService.update(id, clientUpdateDto);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<Object>> delete(@PathVariable Long id) {
        clientService.delete(id);
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
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<Object>> getClientById(@PathVariable Long id) {
        ClientDto client = clientService.getClientById(id);
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
