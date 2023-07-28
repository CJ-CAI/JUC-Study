package com.cj.juc.chapter234;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class serialize_abc2 {
    static  ReentrantLock lock=new ReentrantLock();
    static Condition condition_a=lock.newCondition();
    static Condition condition_b=lock.newCondition();
    static Condition condition_c=lock.newCondition();
    static int flag=0;
    static int count=15;

    public static void main(String[] args) {
        new Thread(()->{
            lock.lock();
            try {
                while (count>0){
                    if(flag==0){
                        flag++;
                        System.out.println("a");
                        count--;
                        condition_b.signal();
                    }
                    else {
                        try {
                            condition_a.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }finally {
                lock.unlock();
            }

        },"t1").start();

        new Thread(()->{
            lock.lock();
            try {
                while (count>0){
                    if(flag==1){
                        flag++;
                        condition_c.signal();
                        System.out.println("b");
                        count--;
                    }
                    else {
                        try {
                            condition_b.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }finally {
                lock.unlock();
            }

        },"t2").start();

        new Thread(()->{
            lock.lock();
            try {
                while (count>0){
                    if(flag==2){
                        flag=0;
                        condition_a.signal();
                        System.out.println("c");
                        count--;
                    }
                    else {
                        try {
                            condition_c.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }finally {
                lock.unlock();
            }

        },"t3").start();
    }
}
