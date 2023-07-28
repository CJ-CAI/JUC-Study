package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;
@Slf4j
public class DaemonTest {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
//            while (true){
//                if(Thread.currentThread().isInterrupted())
//                    break;
//            }
            log.debug("运行结束");
        },"t1");
        Thread t2 = new Thread(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2");
        Thread t3 = new Thread(() -> {
            while (true){
                if(Thread.currentThread().isInterrupted())
                    break;
            }
            log.debug("运行结束");
        },"t3");
//        t1.start();
//        t2.start();
        t3.setDaemon(true);
        t3.start();
        sleep(4000);
//        log.debug("t1:{}",t1.getState());
        log.debug("t3:{}",t3.getState());
        log.debug("cost:{}",System.currentTimeMillis()-start);
    }
}
