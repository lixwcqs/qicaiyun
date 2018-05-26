package com.cqs.qicaiyun.services.lock;

import org.redisson.Redisson;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.locks.Lock;

public class RedissionDistributeLock implements DistributeLock {

    private String name = "default";
    private RedissonClient client;
    private Lock lock;
    private RReadWriteLock rrwlock;

    private static String address = "redis://127.0.0.1:6379";

    public RedissionDistributeLock(String name) {
        client = client();
        this.name = name;
        lock = client.getLock(name);
        rrwlock = client.getReadWriteLock(name);
    }

    public RedissionDistributeLock() {
        client = client();
        lock = client.getLock(name);
        rrwlock = client.getReadWriteLock(name);
    }

    public RedissonClient client() {
        Config config = new Config();
        config.setNettyThreads(2);
        config.useSingleServer().setAddress(address).setConnectionMinimumIdleSize(1).setConnectionPoolSize(4);
        return Redisson.create(config);
    }

    @Override
    public void lock() {

        // 最常见的使用方法
        lock.lock();
//        rrwlock.readLock().lock();
    }

    @Override
    public void lock(String key) {

    }


    @Override
    public void unlock() {
        lock.unlock();
//        rrwlock.readLock().unlock();
    }

    @Override
    public void unlock(String key) {

    }

}
