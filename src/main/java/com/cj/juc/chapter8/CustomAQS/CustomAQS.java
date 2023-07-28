package com.cj.juc.chapter8.CustomAQS;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j
public class CustomAQS {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        MyLock myLock = new MyLock();
        new Thread(()->{
            log.debug("start.....");
            try {
                myLock.lock();
                try {
                    log.debug("running");
                    myLock.lock();
                    try {
                        log.debug("running2");//判断为不可重入
                    }finally {
                        myLock.unlock();
                    }
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("running");
            }finally {
                myLock.unlock();
            }

        }).start();

        sleep(1);
        new Thread(()->{
            log.debug("start.....");
            try {
                myLock.lock();
                try {
                    log.debug("running");
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }finally {
                myLock.unlock();
            }

        }).start();
    }
}
class MyLock implements Lock {
    private MySync sync=new MySync();

    class MySync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
