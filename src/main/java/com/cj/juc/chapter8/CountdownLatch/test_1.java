package com.cj.juc.chapter8.CountdownLatch;

import java.util.concurrent.CountDownLatch;

public class test_1 {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 10; i++) {
            int number=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+":"+number);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}
