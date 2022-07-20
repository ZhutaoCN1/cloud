package com.dream.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.dream.client.ProviderClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/consumer")
@Slf4j
@RefreshScope
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/touch")
    public String getInfo() {
        String forObject = restTemplate.getForObject("http://cloud-provider/provider/hi?name=xiaowangwang", String.class);
        return forObject;
    }

    @Autowired
    ProviderClient providerClient;

    @GetMapping("/feign")
    @SentinelResource(fallback = "handlerFallback")
    public String getInfoByfeign(@RequestParam(required = false) String name) {
        monitor();
        if (StringUtil.equalsIgnoreCase(name, "z")) {
            throw new IllegalArgumentException("参数异常");
        }
        return providerClient.hi(name);
    }

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config")
    public String getConfigInfo() {
        return configInfo;
    }

    //处理异常的回退方法（服务降级）
    public String handlerFallback(@RequestParam(required = false) String name, Throwable e) {
        log.info("--------->>>>服务降级逻辑");
        return "提醒您，服务被降级！异常信息为：" + e.getMessage();
    }

    /**
     * 通过代码 初始化熔断策略
     */
    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule("fallback");
        //熔断策略为异常比例
        rule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
        //异常比例阈值
        rule.setCount(0.7);
        //最小请求数
        rule.setMinRequestAmount(100);
        //统计市场，单位毫秒
        rule.setStatIntervalMs(30000);
        //熔断市场，单位秒
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    /**
     * 自定义事件监听器，监听熔断器状态转换
     */
    public void monitor() {
        EventObserverRegistry.getInstance().addStateChangeObserver("logging",
                (prevState, newState, rule, snapshotValue) -> {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (newState == CircuitBreaker.State.OPEN) {
                        // 变换至 OPEN state 时会携带触发时的值
                        log.error(String.format("%s -> OPEN at %s, 发送请求次数=%.2f", prevState.name(),
                                format.format(new Date(TimeUtil.currentTimeMillis())), snapshotValue));
                    } else {
                        log.error(String.format("%s -> %s at %s", prevState.name(), newState.name(),
                                format.format(new Date(TimeUtil.currentTimeMillis()))));
                    }
                });
    }
}
