package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

@Slf4j(topic = "c.EightLock")
public class ThreadEightLock {
    public static void main(String[] args) {
        Number number = new Number();
        new Thread(()->{
            try {
                number.a();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{number.b();}).start();
    }
}
@Slf4j(topic = "c.Number")
class Number{
    public synchronized void a() throws InterruptedException {
        sleep(1000);
        log.debug("1");
    }
    public static synchronized void b(){
        log.debug("2");
    }
}