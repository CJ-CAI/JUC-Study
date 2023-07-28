package com.cj.juc.chapter8.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class test_1 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("finish");
        });
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    System.out.println("start...");
                    cyclicBarrier.await();
                    System.out.println("end......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
