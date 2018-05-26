package com.cqs.qicaiyun.services.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class DistributeLockClient {
    volatile boolean working = true;


    private void performance(DistributeLock lock2) throws InterruptedException {
        int size = 1 << 1;
        ExecutorService exec = Executors.newFixedThreadPool(size);
        Callable<Integer> task = () -> {
            DistributeLock lock = new RedissionDistributeLock(new Random().nextInt(100) + "");
            int count = 0;
            while (working) {
                lock.lock();
                ++count;
                lock.unlock();
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
        int seconds = 2 << 6;
        TimeUnit.SECONDS.sleep(seconds);
        working = false;
        TimeUnit.MILLISECONDS.sleep(2000);
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

        System.out.println("分布式锁TPS:" + result * 1.0 / seconds);
        exec.shutdown();
        System.exit(0);
    }

    public static void main(String[] args) throws InterruptedException {
        DistributeLockClient client = new DistributeLockClient();
        client.performance(new RedissionDistributeLock());
    }
}
