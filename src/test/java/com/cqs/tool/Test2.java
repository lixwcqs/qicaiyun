package com.cqs.tool;




/**
 * Created by cqs on 2018/5/3.
 */
public class Test2 {

    private int c1 = 0;
    private static int c2 = 0;

//    @Test
//    public void shareMethod() throws Exception {
//        final Test2 test = new Test2();
//        int size = 1000;
//        CountDownLatch latch = new CountDownLatch(size);
//        for (int i = 0; i < size; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    test.add1();
//                    new Test2().add2();
//                    latch.countDown();
//                }
//            }).start();
//        }
//        latch.await();
//        System.out.println(test.c1+"\t"+c2);
//    }

    public synchronized void add1() {
        c1++;
        Thread.yield();
    }

    public synchronized static void add2() {
        c2++;
        Thread.yield();
    }
}
