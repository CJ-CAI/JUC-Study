package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

public class philosopher_1 {
    public static void main(String[] args) {
        chopstick c1 = new chopstick();
        chopstick c2 = new chopstick();
        chopstick c3 = new chopstick();
        chopstick c4 = new chopstick();
        chopstick c5 = new chopstick();

        philosopher p1 = new philosopher(c1, c2);
        p1.setName("p1");
        p1.start();

        philosopher p2 = new philosopher(c2, c1);
        p2.setName("p2");
        p2.start();

        philosopher p3 = new philosopher(c3, c4);
        p3.setName("p3");
        p3.start();

        philosopher p4 = new philosopher(c4, c5);
        p4.setName("p4");
        p4.start();

        philosopher p5 = new philosopher(c5, c1);
        p5.setName("p5");
        p5.start();
    }
}
@Slf4j
class philosopher extends Thread{
    private chopstick left;
    private chopstick right;

    public philosopher(chopstick left, chopstick right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    log.debug("{} edting food", Thread.currentThread().getName());
                }
            }
        }
    }
}
class chopstick{

}
