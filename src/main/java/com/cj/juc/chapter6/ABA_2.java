package com.cj.juc.chapter6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

import static java.lang.Thread.sleep;
@Slf4j
public class ABA_2 {
    static AtomicStampedReference<String> ref=new AtomicStampedReference<>("A",0);
    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        String prev=ref.getReference();
        int expect=ref.getStamp();
        new Thread(()->{
            ref.set("B",1);
        }).start();
        new Thread(()->{
            ref.set("A",2);
        }).start();
        sleep(1000);
        System.out.println(ref.compareAndSet(prev, "A", expect, 1));
    }
}
