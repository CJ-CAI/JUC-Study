package com.cj.juc.chapter6;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdater_Test {
    public static void main(String[] args) {
        test test = new test();
        AtomicReferenceFieldUpdater<test, String> updater = AtomicReferenceFieldUpdater.newUpdater(test.class, String.class, "name");
        updater.compareAndSet(test,null,"peter");
        System.out.println(test.toString());
    }
}

class test{
    volatile String name;

    @Override
    public String toString() {
        return "test{" +
                "name='" + name + '\'' +
                '}';
    }
}
