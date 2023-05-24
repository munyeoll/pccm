package com.demo.pccm.domain.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/client")
@Controller
public class ClientPageController {

    @GetMapping("/list")
    public String clientList() {
        return "client/client-list";
    }

    @GetMapping("/info")
    public String clientInfo(@RequestParam Long id, Model model) {
        model.addAttribute("id", id);
        return "client/client-info";
    }

}
