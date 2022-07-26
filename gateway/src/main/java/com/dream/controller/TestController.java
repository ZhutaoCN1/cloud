package com.dream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author zhutao
 * @Date 2022-7-26
 */
@RestController
public class TestController {

    @GetMapping("/get")
    public void print() {
        System.out.println("123");
    }
}
