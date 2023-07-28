package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j
public class ReentrantLock_Test1 {
    static ReentrantLock lock=new ReentrantLock();
    static Object lock2=new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("尝试锁");
                lock.lockInterruptibly();
                log.debug("执行临界区代码");
            } catch (InterruptedException e) {
                log.debug("获取锁失败...");
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1");
//        new Thread(()->{
//            synchronized (lock2){
//                try {
//                    sleep(10000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        },"t2").start();

        Thread t3 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t3");
//        t3.start();

//        t1.start();
//        t1.interrupt();
//        t3.interrupt();

        t1.start();
        t1.interrupt();
        lock.lock();
        sleep(2000);
        lock.unlock();
    }
}
