package com.cj.juc.chapter234;

import com.cj.juc.chapter234.Utils.GuardedObject;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;
@Slf4j
public class GuardedSuspend {
    public static void main(String[] args) throws InterruptedException {
        GuardedObject guarded = new GuardedObject("1",null);
        new Thread(()->{
            log.debug("吃:{}",guarded.getResponse(2000));
        },"consumer").start();
        sleep(1000);

        new Thread(()->{
            String bread = new String("bread");
            guarded.setResponse(bread);
        },"product").start();
    }
}
