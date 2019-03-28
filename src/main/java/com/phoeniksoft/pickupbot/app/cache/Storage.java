package com.phoeniksoft.pickupbot.app.cache;

public interface Storage<K, V> {

    void put(K key, V value);

    V get(K key);

    boolean remove(K key);
}
