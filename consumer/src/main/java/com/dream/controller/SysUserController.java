package com.dream.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dream.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author BigZ
 * @since 2022-07-25
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @GetMapping("/feign")
    @SentinelResource(fallback = "handlerFallback")
    public void getInfoByfeign() {
        userService.createUser();
    }

}
