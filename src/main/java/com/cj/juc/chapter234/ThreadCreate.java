package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.createThread")
public class ThreadCreate {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        });
        thread1.start();
        log.debug("running");

        Thread thread2 = new Thread(() -> {
            log.debug("running");
        });
        thread2.start();

        Thread thread3 = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        thread3.start();
    }
}
