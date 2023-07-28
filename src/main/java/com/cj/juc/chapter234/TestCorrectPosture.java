package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCorrectPosture {
    private static boolean hasSmoke = false;
    private static boolean hasFood = false;

    public static void main(String[] args) {
        Room room = new Room();
        new Thread(() -> {
            synchronized (Room.class) {
                while (!hasSmoke) {
                    log.debug("没有烟，继续等待.....");
                    try {
                        Room.class.wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("抽烟,开始工作......");
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (Room.class) {
                while (!hasFood) {
                    log.debug("没有外卖，继续等待.....");
                    try {
                        Room.class.wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("吃完外卖,开始工作......");
            }
        }, "小女").start();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                synchronized (room){
                    log.debug("其他人:开始工作");
                }
            },Integer.toString(i)).start();
        }
        new Thread(() -> {
            synchronized (Room.class) {
                hasSmoke = true;
                hasFood=true;
                Room.class.notify();
            }
        }, "烟").start();
    }
}

class Room {

}