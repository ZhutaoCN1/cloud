package com.dream.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud-provider",fallback = ProviderClientFallback.class)
public interface ProviderClient {

    @GetMapping("/provider/hi")
    String hi(@RequestParam(value = "name", defaultValue = "forezp", required = false) String name);

}
