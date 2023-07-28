package com.cj.juc.chapter234;

public class serialize_wait {
    static boolean serialized = false;

    public static void main(String[] args) {
        Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                int count=5;
                while (count-->0) {
                    if (!serialized) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (serialized) {
                        System.out.println("b");
                        serialized = !serialized;
                    }
                    lock.notify();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                int count=5;
                while (count-->0) {
                    if (serialized) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!serialized) {
                        System.out.println("a");
                        serialized = !serialized;
                    }
                    lock.notify();
                }
            }
        }, "t1").start();
    }
}
