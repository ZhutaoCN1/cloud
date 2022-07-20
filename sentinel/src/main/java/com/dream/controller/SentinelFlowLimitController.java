package com.dream.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sentinel")
public class SentinelFlowLimitController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/testA")
    public String testA() {
        return "服务访问成功------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "服务访问成功------testB";
    }

    @GetMapping("/testC")
    @SentinelResource(value = "服务c",blockHandler = "blockHandlerTestD")
    public String testC(@RequestParam String name) {
        initFlowRules();    //通过代码来控制流量
        return name+"-:-服务访问成功------testC";
    }

    /**
     * 通过代码定义流量控制规则
     */
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        //定义一个限流规则对象
        FlowRule rule = new FlowRule();
        //资源名称
        rule.setResource("服务c");
        //限流阈值的类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置 QPS 的阈值为 2
        rule.setCount(2);
        rules.add(rule);
        //定义限流规则
        FlowRuleManager.loadRules(rules);
    }


    /**
     * 限流之后的逻辑  入参数要和原方法一致，额外加一个BlockException exception。返回结果要和原方法一致！
     * @param exception
     * @return
     */
    public String blockHandlerTestD(String name,BlockException exception) {
        log.info(Thread.currentThread().getName() + name +"服务C访问失败! 您已被限流，请稍后重试");
        return "服务C访问失败! 您已被限流，请稍后重试";
    }
}
