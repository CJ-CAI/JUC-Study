package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

@Slf4j(topic = "c.ParkTest")
public class TestPark {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.debug("park....");
            LockSupport.park();
            log.debug("unPark1....");
//            log.debug("{}",Thread.currentThread().isInterrupted());   //不会重置
            log.debug("{}",Thread.interrupted());//会重置interrupted的值
            LockSupport.park();
            log.debug("unPark2....");
        });
        thread.start();
        sleep(1);
        thread.interrupt();
    }
}
