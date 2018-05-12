package com.cqs.tool;

import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 2018/5/7.
 */
public class DeadLock {

    public static void main(String[] args) {
//        example1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DeadLock.example2();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DeadLock2.example2();
            }
        }).start();
    }




    synchronized static void example2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DeadLock2.example2();
    }



    private static void example1() {
        Object ob = new Object();
        Object ob2 = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ob){
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (ob2){

                        };
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ob2){
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (ob){
                            //
                        };
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
        System.out.println("EXIST...");
    }
}
class DeadLock2{
    public synchronized static void example2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DeadLock.example2();
    }
}