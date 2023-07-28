package com.cj.juc.chapter8.ThreadPoolExecutor;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Constructor_test {
    public static void main(String[] args) throws InterruptedException {
        new ReentrantLock();
        ExecutorService executorService_Fixed = Executors.newFixedThreadPool( 10);
        ExecutorService executorService_Single = Executors.newSingleThreadExecutor();
        ExecutorService executorService_Cache = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SynchronousQueue<String> strings = new SynchronousQueue<>(true);
//        strings.add("s");
    /*    new Thread(()->{
            try {
                strings.put("CJ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                strings.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(strings.take());*/
        for (int i = 0; i < 10; i++) {
            int id=i;
            executorService_Cache.execute(()->{
                synchronized (executorService_Cache) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("running...." + id + Thread.currentThread().getName());
                }
                });
        }
        executorService_Cache.shutdown();
        executorService_Cache.awaitTermination(3, TimeUnit.SECONDS);
        executorService_Cache.shutdownNow();//会立即打断所有线程
        System.out.println("shutdown....");



    }

}
