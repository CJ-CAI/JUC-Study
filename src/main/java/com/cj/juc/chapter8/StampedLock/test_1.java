package com.cj.juc.chapter8.StampedLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

public class test_1 {
        public static void main(String[] args) {
            ReentrantLock reentrantLock = new ReentrantLock();
            reentrantLock.lock();
            DataContainerStamped dataContainerStamped = new DataContainerStamped(67);

            for (int i = 0; i < 5; i++) {
                new Thread(()->{
                    dataContainerStamped.write(74);
                }).start();
            }

            for (int i = 0; i < 5; i++) {
                new Thread(()->{
                    dataContainerStamped.read();
                }).start();
            }
        }
}
@Slf4j
class DataContainerStamped{
    private int data;
    private final StampedLock lock=new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }

    public int read(){
        long stamp = lock.tryOptimisticRead();
        if (lock.validate(stamp)){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("tryOptimisticRead read data,stamp:{},data:{}",stamp,data);
            return data;
        }
        stamp = lock.readLock();
        try {
            log.debug("readLock read data,stamp:{},data:{}",stamp,data);
            return data;
        }finally {
            lock.unlock(stamp);
        }
    }
    public void  write(int newData){
        this.data=newData;
        long stamp = lock.writeLock();
        try {
            try {
                sleep(2000);
                /*lock.writeLock();
                log.debug("ReentrantLock...");*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("update data value old:{}->new:{}",data,newData);
            this.data=newData;
        }finally {
            lock.unlock(stamp);
        }
    }
}
