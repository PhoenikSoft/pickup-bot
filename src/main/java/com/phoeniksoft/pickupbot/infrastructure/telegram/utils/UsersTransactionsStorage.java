package com.phoeniksoft.pickupbot.infrastructure.telegram.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.phoeniksoft.pickupbot.app.cache.Storage;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class UsersTransactionsStorage implements Storage<Long, Boolean> {

    private Cache<Long, Boolean> usersWithRequestedInput;
    private final String storageConfig;

    @PostConstruct
    private void init() {
        usersWithRequestedInput = CacheBuilder.from(storageConfig).build();
    }

    @Override
    public void put(Long key, Boolean value) {
        usersWithRequestedInput.put(key, value);
    }

    @Override
    public Boolean get(Long key) {
        return usersWithRequestedInput.asMap().getOrDefault(key, Boolean.FALSE);
    }

    @Override
    public boolean remove(Long key) {
        usersWithRequestedInput.invalidate(key);
        return true;
    }
}
