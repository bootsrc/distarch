package com.appjishu.swordboot.controller;

import com.appjishu.swordboot.util.TestUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController
public class HomeController {
    @RequestMapping("")
    public String index() {
        return "Access sword-springboot OK.";
    }

    @RequestMapping("/test")
    public String test() {
        TestUtil.testAppContextHolder();
        return "Test done.";
    }
}
