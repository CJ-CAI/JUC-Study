package com.cj.juc.chapter6;

import com.cj.juc.chapter6.util.GarbageBag;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class ABA_3 {
    public static void main(String[] args) {
        GarbageBag bag = new GarbageBag("装满了垃圾");
        AtomicMarkableReference<GarbageBag> atomicMarkableReference=new AtomicMarkableReference<>(bag,true);
        GarbageBag bag_1 = new GarbageBag("空垃圾");
        new Thread(()->{
            atomicMarkableReference.set(bag_1,false);
        }).start();

        System.out.println(atomicMarkableReference.compareAndSet(bag, bag_1, true, false));
        System.out.println(atomicMarkableReference.getReference().toString());

    }
}
