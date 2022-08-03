package com.dream.client;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProviderClientFallback implements ProviderClient {
    @Override
    public String hi(String name) {
        return name + "sorry";
    }


    @Override
    public void createUser() {
        try {
            log.warn("开始手动回滚");
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
            log.warn("手动回滚结束");
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }
}
