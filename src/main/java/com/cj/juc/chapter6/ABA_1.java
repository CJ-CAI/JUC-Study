package com.cj.juc.chapter6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.LongAdder;

import static java.lang.Thread.sleep;

@Slf4j
public class ABA_1 {
    static AtomicReference<String> atomicReference=new AtomicReference<>("A");
    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        String prev=atomicReference.get();
        new Thread(()->{
            atomicReference.getAndUpdate(p->"B");
        }).start();
        new Thread(()->{
            atomicReference.getAndUpdate(p->"A");
        }).start();
        sleep(1000);
        System.out.println(atomicReference.compareAndSet(prev, "B")+":"+atomicReference.get());
    }
}
