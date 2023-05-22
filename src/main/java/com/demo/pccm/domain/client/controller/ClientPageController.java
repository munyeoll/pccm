package com.demo.pccm.domain.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/client")
@Controller
public class ClientPageController {

    @GetMapping("/list")
    public String clientList() {
        return "client/client-list";
    }

    @GetMapping("/info")
    public String clientInfo() {
        return "client/client-info";
    }

}
