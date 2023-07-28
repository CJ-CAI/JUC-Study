package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j
public class TestCorrectPosture_2 {
    private static boolean hasSmoke = false;
    private static boolean hasFood = false;

    static Room_r room_r=new Room_r(false);

    static Condition waitCigarette=room_r.newCondition();
    static Condition waitTakeout=room_r.newCondition();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            room_r.lock();
            try {
                log.debug("有烟没？{}",hasSmoke);
                while (!hasSmoke){
                    log.debug("没烟休息会");
                    try {
                        waitCigarette.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("收到烟，开始工作...");
            }finally {
                room_r.unlock();
            }
        }, "小南").start();

        new Thread(() -> {
            room_r.lock();
            try {
                log.debug("有外卖没？{}",hasFood);
                while (!hasFood){
                    log.debug("没有外卖，休息会");
                    try {
                        waitTakeout.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("收到外卖，开始工作");
            }finally {
                room_r.unlock();
            }
        }, "小女").start();

        /*for (int i = 0; i < 100; i++) {
            new Thread(()->{
                synchronized (room){
                    log.debug("其他人:开始工作");
                }
            },Integer.toString(i)).start();
        }*/
        sleep(1000);
        room_r.lock();
        try {
            sleep(1000);
            hasSmoke=true;
            waitCigarette.signal();
            sleep(2000);
            hasFood=true;
            waitTakeout.signal();//唤醒后，非公平锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            room_r.unlock();
        }
    }
}

class Room_r extends ReentrantLock {
    public Room_r(boolean fair) {
        super(fair);
    }
}