package com.cqs.tool;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cqs on 2017/10/22.
 */
public class Test1 {

    private static class Count {
        int count = 0;
    }

    private volatile static Count c = new Count();


    @Test
    public void share() throws Exception {
//        ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 2000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Test1().add();
                }
            }).start();
        }

        System.out.println(c.count);
    }

    public  synchronized static void add(){
            c.count++;
            Thread.yield();
    }

    public static void main1(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);

//        new Thread(){
//            public void run() {
//                try {
//                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
//                    Thread.sleep(3000);
//                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
//                    latch.countDown();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            };
//        }.start();

        new Thread(){
            public void run() {
                try {
                    System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) throws InterruptedException {
        int size = 8;
        Semaphore semaphore = new Semaphore(size);

        AtomicInteger c = new AtomicInteger();
        AtomicInteger c2 = new AtomicInteger();
        ExecutorService exec = Executors.newFixedThreadPool(size);
        while (true) {
            semaphore.acquire();
            System.out.println("主线程取出速度:" + c.addAndGet(1));
            semaphore.release();
            exec.submit(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                semaphore.acquire();
                                TimeUnit.MICROSECONDS.sleep(2000);
                                System.out.println(Thread.currentThread() + "子线程消费数量：" + c2.addAndGet(1));
                                semaphore.release();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            TimeUnit.SECONDS.sleep(1);
        }

    }

}
