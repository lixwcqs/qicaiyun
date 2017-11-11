package com.cqs.qicaiyun.common.jwt;

import com.cqs.qicaiyun.system.entity.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 2017/11/2.
 */
public class TokenManager {

    private final static TokenManager instance = new TokenManager();
    private Cache<String, User> tokens = CacheBuilder.newBuilder().expireAfterAccess(60, TimeUnit.MINUTES)
            .initialCapacity(20).maximumSize(10000).weakKeys()
            .concurrencyLevel(1).build();

    private TokenManager() {
    }

    public static TokenManager getInstance() {
        return instance;
    }

    public synchronized void addToken(String token, User user) {
        tokens.put(token, user);
    }

    public synchronized User getToken(String token) {
        return tokens.getIfPresent(token);
    }

    //
    public synchronized void invalidate(String token) {
        tokens.invalidate(token);
    }

    public synchronized boolean checkValidate(String token) {

        User user = tokens.getIfPresent(token);
        System.out.println(tokens.asMap().keySet());
        return user != null;
    }

}
