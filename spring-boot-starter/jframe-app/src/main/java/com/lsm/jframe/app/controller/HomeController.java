package com.lsm.jframe.app.controller;

import com.lsm.jframe.core.api.JframeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

    @Autowired
    private JframeService jframeService;

    @RequestMapping("")
    public String index() {
        return "jframe-app works.";
    }

    @RequestMapping("starter/{word}")
    public String starter(@PathVariable String word) {
        String result = jframeService.wrap(word);
        return result;
    }
}
