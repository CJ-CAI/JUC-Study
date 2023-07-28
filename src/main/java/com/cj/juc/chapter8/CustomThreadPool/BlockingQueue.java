package com.cj.juc.chapter8.CustomThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j

public class BlockingQueue<T> {
    private Deque<T> queue=new ArrayDeque<>();
    private int Capacity;
    ReentrantLock lock=new ReentrantLock();
    private Condition fullWaitSet=lock.newCondition();
    private Condition emptyWaitSet=lock.newCondition();

    public BlockingQueue(int capacity) {
        Capacity = capacity;
    }

    public T pull() {
        lock.lock();
        try {
            while (queue.isEmpty()){
                try {
                    emptyWaitSet.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            T t=queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public T pull_time(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos=unit.toNanos(timeout);
            while (queue.isEmpty()){
                try {
                    if(nanos<=0)
                        return null;
//                    System.out.println(nanos);
                    nanos= emptyWaitSet.awaitNanos(nanos);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            T t=queue.removeFirst();
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public void put(T task) {
        lock.lock();
        try {
            while (queue.size()>=Capacity){
                try {
                    fullWaitSet.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            queue.addLast(task);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }

    }

    public void put_time(T task,long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nanos=timeUnit.toNanos(timeout);
            while (queue.size()>=Capacity){
                try {
                    if (nanos<=0)
                        return;
                    nanos= fullWaitSet.awaitNanos(nanos);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            queue.addLast(task);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }
    public int size(){
        lock.lock();
        try {
            return queue.size();
        }finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy,T task){
        lock.lock();
        try {
            if(queue.size()==Capacity)
                rejectPolicy.reject(this,task);
            else {
                log.debug("加入任务列{}",task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }



}
