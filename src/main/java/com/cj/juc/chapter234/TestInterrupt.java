package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Interrupt")
public class TestInterrupt {
    public static void main(String[] args) throws InterruptedException {
//        Test1();
//        Test2();
        Test3();
    }
    public static void Test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        t1.join();
        log.debug("interrupt flag:{}",t1.isInterrupted());
    }
    public static void Test2() throws InterruptedException {
        Thread t2 = new Thread(() -> {
           while (true){
               if(Thread.currentThread().isInterrupted()){
                   log.debug("It was interrupted,break while");
                   break;
               }
           }
        }, "t1");
        t2.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t2.interrupt();
    }
    public static void Test3(){
        Thread t3 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                log.debug("listening is normal");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    throw new RuntimeException(e);
                }
            }
        }, "t3");
        t3.start();
//        t3.interrupt();
    }
}
