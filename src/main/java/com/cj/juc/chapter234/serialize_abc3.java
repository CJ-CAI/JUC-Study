package com.cj.juc.chapter234;

import java.util.concurrent.locks.LockSupport;

public class serialize_abc3 {

    static int flag=0;
    static int count=15;
    static Thread t1,t2,t3;

    public static void main(String[] args) {
        System.out.println();

        t1 = new Thread(() -> {
            while (count>0){
                LockSupport.park();
                System.out.println("a");
                count--;
                LockSupport.unpark(t2);
            }
        }, "t1");

        t2 = new Thread(() -> {
            while (count>0){
                count--;
                LockSupport.park();
                System.out.println("b");
                LockSupport.unpark(t3);
            }
        }, "t2");

        t3 = new Thread(() -> {
            while (count>0){
                count--;
                LockSupport.park();
                System.out.println("c");
                LockSupport.unpark(t1);
            }

        }, "t3");

        t1.start();t2.start();t3.start();

        LockSupport.unpark(t1);
    }
}
