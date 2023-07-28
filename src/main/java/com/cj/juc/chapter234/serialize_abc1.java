package com.cj.juc.chapter234;

public class serialize_abc1 {
    static Object lock=new Object();
    static int flag=0;

    static int count=15;
    public static void main(String[] args) {
        new Thread(()->{
            while (count>0) {
                synchronized (lock) {
                    if (flag == 0) {
                        flag++;
                        System.out.println("a");
                        lock.notifyAll();
                        count--;
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"t1").start();

        new Thread(()->{
            while (count>0) {
                synchronized (lock) {
                    if (flag == 1) {
                        flag++;
                        System.out.println("b");
                        lock.notifyAll();
                        count--;
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"t2").start();

        new Thread(()->{
            while (count>0) {
                synchronized (lock) {
                    if (flag == 2) {
                        flag=0;
                        System.out.println("c");
                        lock.notifyAll();
                        count--;
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"t3").start();

    }
}
