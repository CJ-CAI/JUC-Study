package com.cj.juc.chapter6;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInteger_1 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        while (true) {
            int prev=atomicInteger.get();
            if (atomicInteger.compareAndSet(prev, 15))
                break;
        }
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.updateAndGet(p->p*10));
        System.out.println(atomicInteger.accumulateAndGet(10, (p, x) -> p + x));
    }
}
