package com.dream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zhutao
 * @Date 2022-7-26
 */
@RestController
public class TestController {

    @GetMapping("/get")
    public List<Object> print() {
        return new ArrayList<>();
    }
}
