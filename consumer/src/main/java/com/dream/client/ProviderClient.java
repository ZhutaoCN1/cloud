package com.dream.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud-provider",fallback = ProviderClientFallback.class)
//@FeignClient(value = "cloud-provider")
public interface ProviderClient {

    @GetMapping("/provider/hi")
    String hi(@RequestParam(value = "name", defaultValue = "forezp", required = false) String name);

    @PostMapping("/sys-user")
    void createUser();
}
