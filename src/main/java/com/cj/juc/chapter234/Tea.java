package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j(topic = "c.Tea")
public class Tea {
    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            log.debug("Wash the kettle for 1 minute");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("Boil water for 15 minutes");
            try {
                sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "小李");
        Thread t2 = new Thread(() -> {
            try {
                log.debug("Wash the teapot for 1 minute");
                sleep(1000);
                log.debug("Wash the cup for 2 minute");
                sleep(2000);
                log.debug("Hold the cup for 1 minute");
                sleep(1000);
                t1.join();
                log.debug("make Tea");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "小王");
        t1.start();
        t2.start();
        t2.join();
        Long finish=System.currentTimeMillis();
        log.debug("make tea cost {} minutes",finish-start);
    }
}
