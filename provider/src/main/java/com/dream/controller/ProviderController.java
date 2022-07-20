package com.dream.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
@Slf4j
public class ProviderController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name",defaultValue = "zhutao",required = false) String name) {
        return "hi~"+name+".i am "+port;
    }
}
