package com.cqs.qicaiyun.modules;

import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过随机数求圆周率
 * 思路:借助概率实现实现
 * 随机生成x~[0,1] y~[0,1]的点N次
 * 点落在半径为1的四分之一的圆圈内的次数为M
 * 那么概率P=M*1.0/N就是四分之一的面积
 *
 * 利用面积公式
 * p = area/4 = PI * (R^2) / 4
 * p =  出现在四分之一圆内的次数/总的次数
 * 而R是等于1的所以
 * PI=4*p
 * PI=P=M*4.0/N
 * Created by cqs on 2018/5/6.
 */
public class PI {
    private static Random r = new Random(1);
    private static int times = 1 << 27;//

    //多线程版本
    public static double calculatePI() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int size = 2;//本机四核 为了避免频繁上下文切换 设置2个线程数 配置高的话 可以调高参数
        CountDownLatch latch = new CountDownLatch(size);
        AtomicInteger total = new AtomicInteger();
        ExecutorService exec = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    int count = 0;
                    for (int j = 0; j < times / size; j++) {
                        count += randomPos();
                    }
                    total.addAndGet(count);
                    latch.countDown();
                }
            });
        }
        exec.shutdown();
        try {
//            latch.await(10,TimeUnit.SECONDS);
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stopWatch.stop();
        System.out.println("多线程运行时间:"+stopWatch.getTotalTimeMillis() / 1000.0);
        return total.get() * 4.0 / times;

    }

    //单线程
    public static double calculatePI2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        double area = 0;
        for (int i = 0; i < times; i++) {
            area += randomPos();
        }
        stopWatch.stop();
        System.out.println("单线程运行时间:"+stopWatch.getTotalTimeMillis() / 1000.0);
        return area * 4.0 / times;
    }

    //随机生成一个点x~[0,1],y~[0,1]
    //落在半径为1的区域内返回1 否则返回0
    private static int randomPos() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        double dis = x * x + y * y;
        return dis <= 1 ? 1 : 0;
    }

    public static void main(String[] args) {

        System.out.println(calculatePI());
        System.out.println(calculatePI2());
    }
}
