package com.appjishu.jdbc.demo.controller;

import com.appjishu.jdbc.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController
public class HomeController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("")
    public String index() {
        return "Access sword-springboot OK.";
    }

    @RequestMapping("/test")
    public String test() {
        demoService.handle();
        return "Test done.";
    }
}
