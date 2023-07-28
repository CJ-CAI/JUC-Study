package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;
@Slf4j(topic = "c.ThreadStatus")
public class ThreadStatus {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while (true){}
        },"t1");
        new Thread(()->{},"t1");
        Thread t2 = new Thread(() -> {
            synchronized (ThreadStatus.class) {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2");
        Thread t3 = new Thread(()->{
        },"t3");
        Thread t4 = new Thread(()->{
        },"t4");
        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t5");
        Thread t6 = new Thread(() -> {
            synchronized (ThreadStatus.class) {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t6");


        t1.start();
        t2.start();
        t3.start();
        t5.start();
        t6.start();
        sleep(1000);
        log.debug("t1 status:{}",t1.getState());
        log.debug("t2 status:{}",t2.getState());
        log.debug("t3 status:{}",t3.getState());
        log.debug("t4 status:{}",t4.getState());
        log.debug("t5 status:{}",t5.getState());
        log.debug("t6 status:{}",t6.getState());
    }
}
