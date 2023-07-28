package com.cj.juc.chapter8.Semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

@Slf4j
public class test1 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    log.debug("running....");
                    sleep(1000);
                    log.debug("end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
