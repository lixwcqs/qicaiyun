package com.cqs.qicaiyun.modules.zk.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Deprecated
public class ExclusiveLockClient {
    volatile boolean working = true;

    //    private String hostPort = "192.168.2.108:2181";
    private String hostPort = "192.168.2.116:2181";

    private InterProcessMutex interProcessMutex() {
        //1 重试策略：初试时间为1s 重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework framework = CuratorFrameworkFactory.builder()
                .connectString(hostPort)
                .retryPolicy(retryPolicy)
                .build();

        framework.start();
        return new InterProcessMutex(framework, "/exclusiveLock");
    }

    public static void main(String[] args) throws InterruptedException {
        ExclusiveLockClient client = new ExclusiveLockClient();
        client.performance();
    }

    private void performance() throws InterruptedException {
        int size = 1;
        ExecutorService exec = Executors.newFixedThreadPool(size);
        InterProcessMutex mutex = interProcessMutex();
        Callable<Integer> task = () -> {
            int count = 0;
            while (working) {
                mutex.acquire();
                ++count;
                mutex.release();
            }
            System.out.println(Thread.currentThread() + "\t" + count);
            return count;
        };
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(task);
        }
        List<Future<Integer>> futures = new ArrayList<>();
        new Thread(() -> {
            try {
                futures.addAll(exec.invokeAll(tasks));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start();
        int seconds = 30;
        TimeUnit.SECONDS.sleep(30);
        working = false;
        TimeUnit.MILLISECONDS.sleep(800);
        System.out.println(futures.size());
        int result = futures.stream().mapToInt(f -> {
            try {
                Integer times = f.get();
                System.out.println(Thread.currentThread() + " time:" + times);
                return times;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return 1;
            }
        }).sum();

        System.out.println("zookeeper 分布式锁TPS:" + result * 1.0 / seconds);
        exec.shutdown();
    }
}
