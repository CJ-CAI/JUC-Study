package com.cj.juc.chapter8.CustomThreadPool;

import lombok.extern.slf4j.Slf4j;


import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@Slf4j
public class Test {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2,2,5, TimeUnit.SECONDS,(queue,task)->{
            log.debug("blocking....");
            queue.put_time(task,3,TimeUnit.SECONDS);
        });
        for (int i = 0; i < 5; i++) {
            int number=i;
            threadPool.execute(()->{
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}",number);
            });
        }

    }
}
