package com.cj.juc.chapter234;

import java.util.concurrent.locks.LockSupport;

public class serialize_SupportLock {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("b");
        }, "t1");
        t1.start();

        new Thread(()->{
            System.out.println("a");
            LockSupport.unpark(t1);
        },"t2").start();
    }
}
