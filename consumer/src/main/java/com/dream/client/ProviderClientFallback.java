package com.dream.client;

import org.springframework.stereotype.Component;

@Component
public class ProviderClientFallback implements ProviderClient {
    @Override
    public String hi(String name) {
        return name + "sorry";
    }


    @Override
    public void createUser() {

    }
}
