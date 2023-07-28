package com.cj.juc.chapter7;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Thread.sleep;
@Slf4j
public class Pools {

    public static void main(String[] args) {
        pool pool = new pool(3);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection connection = pool.get();
                    try {
                        sleep(1000);
                        log.debug("connected.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pool.free(connection);
                }
            }).start();
        }
    }

}
@Slf4j
class pool{
    private final int PoolSize;
    private Connection[] connections;
    private AtomicIntegerArray states;

    public pool(int poolSize) {
        PoolSize = poolSize;
        connections=new Connection[poolSize];
        states=new AtomicIntegerArray(new int[poolSize]);
    }

    public Connection get(){
        for (int i = 0; i < PoolSize; i++) {
            if(states.get(i)==0){
                    states.compareAndSet(i,0,1);
                    return connections[i];
            }
        }
        synchronized (this){
            try {
                log.debug("waiting....");
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public void free(Connection connection){
        for (int i = 0; i < PoolSize; i++) {
            if (connection==connections[i]){
                states.compareAndSet(i,1,0);
                synchronized (this){
                    this.notify();
                }
                break;
            }
        }
    }
}
