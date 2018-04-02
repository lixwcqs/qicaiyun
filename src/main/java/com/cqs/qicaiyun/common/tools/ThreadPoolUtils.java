package com.cqs.qicaiyun.common.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Holder模式生成ThreadPoolExecutor单例
 * 特点：懒加载 + 线程安全
 * Created by cqs on 2017/11/11.
 */
public class ThreadPoolUtils {

    public static ExecutorService getInstance() {
        return Holder.instance;
    }

    private final static class Holder {

        private static final ExecutorService instance = getInstance();

        private static ExecutorService getInstance() {
            ThreadFactory tf = r -> {
                Thread t = new Thread(r);
                t.setName("自定义线程-qicaiyun-"+t.getId());
                return t;
            };

            return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() , tf);
        }
    }
}
