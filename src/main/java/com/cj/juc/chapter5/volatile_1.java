package com.cj.juc.chapter5;

import static java.lang.Thread.sleep;

public class volatile_1 {
     volatile static boolean stop;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (!stop){
            }
        },"t1").start();
        sleep(1000);
        stop=true;
    }
}
