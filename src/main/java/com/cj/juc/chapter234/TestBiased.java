package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import static java.lang.Thread.sleep;

@Slf4j
public class TestBiased {
    public static void main(String[] args) throws InterruptedException {
//        sleep(40000);
        Dog dog = new Dog();
        log.debug(ClassLayout.parseInstance(dog).toPrintable());
//        new Thread(()->{
//            synchronized (dog){
//            try {
//                sleep(2000);
//                log.debug(ClassLayout.parseInstance(dog).toPrintable());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }}
//        }).start();
        synchronized (dog){
            sleep(2000);
            log.debug(ClassLayout.parseInstance(dog).toPrintable());
        }
        log.debug(ClassLayout.parseInstance(dog).toPrintable());
    }
}
class Dog{
    private boolean myboolean = true;
}
