package com.cqs.qicaiyun.services.lock;

public interface DistributeLock {
    void lock();

    void lock(String key);

    void unlock();

    void unlock(String key);
}
