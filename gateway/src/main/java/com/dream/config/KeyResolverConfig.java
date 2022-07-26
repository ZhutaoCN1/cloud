package com.dream.config;

import com.dream.requestRateLimiter.HostAddrKeyResolver;
import com.dream.requestRateLimiter.UriKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author zhutao
 * @Date 2022-7-26
 */
@Configuration
public class KeyResolverConfig {

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

    //只有有一个实现类
//    @Bean
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }

}
