package com.cj.juc.chapter8.ReentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class DataContainer_1 {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();
        DataContainer data = new DataContainer("data");

        new Thread(()->{
            data.white();
//            data.read();
        }).start();
        sleep(1000);
        new Thread(()->{
            data.read();
            data.white();
        }).start();

    }
}
@Slf4j
class DataContainer{
    private Object data;
    private ReentrantReadWriteLock rw=new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock=rw.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock=rw.writeLock();

    public DataContainer(Object data) {
        this.data = data;
    }

    public Object read() {
        readLock.lock();
        try {
            log.debug("read data....");
            sleep(1000);

           /* writeLock.lock();
            try {
                log.debug("Reentrant write data");
            }finally {
                writeLock.unlock();
            }*/

            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            readLock.unlock();
        }
    }

    public void white() {
        writeLock.lock();
        try {
            log.debug("writing data....");
            sleep(1000);
            readLock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
