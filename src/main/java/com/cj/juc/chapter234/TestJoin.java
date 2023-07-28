package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Join")
public class TestJoin {
    static int r = 0;

    static int r1 = 0;
    static int r2 = 0;
    static int r3 = 0;

    public static void main(String[] args) throws InterruptedException {
//        Test1();
//        Test2();
        Test3();
    }

    public static void Test1() throws InterruptedException {
        log.debug("start");
        Thread t1 = new Thread(() -> {
            log.debug("start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r = 10;
            log.debug("final");
        }, "t1");
        t1.start();
        t1.join();
        log.debug("final");
        log.debug("r=" + r);
    }

    public static void Test2() throws InterruptedException {
        log.debug("start");
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r1 = 10;
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r2 = 20;
        }, "t2");
        long start = System.currentTimeMillis();
        t2.start();
        t1.start();
        log.debug("join start");
        t2.join();
        log.debug("t2 join final");
        t1.join();
        log.debug("t1 join final");

        long end = System.currentTimeMillis();
        log.debug("r1:{} r2:{} cost :{}", r1, r2, end - start);
    }

    public static void Test3() throws InterruptedException {
        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r3 = 30;
        }, "t3");
        long start = System.currentTimeMillis();
        t3.start();
        t3.join(5000);
        long end = System.currentTimeMillis();
        log.debug("r3:{} cost:{}",r3,end-start);
    }
}
