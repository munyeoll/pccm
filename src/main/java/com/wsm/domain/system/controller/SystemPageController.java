package com.wsm.domain.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/system")
@Controller
public class SystemPageController {

    @GetMapping("/common-code-mng")
    public String clientList() {
        return "system/common-code-mng";
    }
}
