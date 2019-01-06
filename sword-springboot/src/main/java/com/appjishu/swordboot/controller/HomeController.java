package com.appjishu.swordboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController
public class HomeController {
    @RequestMapping("")
    public String index() {
        return "Access sword-springboot OK.";
    }
}
