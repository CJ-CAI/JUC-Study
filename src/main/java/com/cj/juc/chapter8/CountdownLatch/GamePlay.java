package com.cj.juc.chapter8.CountdownLatch;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;

public class GamePlay {
    public static void main(String[] args) {
        Random random = new Random();
        String[] loads = new String[10];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
            System.out.println();
            System.out.println("Game Start....");
        });
        for (int i = 0; i < 10; i++) {
            int id=i;
            new Thread(()->{
                try {
                    for (int time = 0; time < 101; time++) {
                        sleep(random.nextInt(100));
                        loads[id]=time+"%";
                        System.out.print("\r"+ Arrays.toString(loads));
                    }
                cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
