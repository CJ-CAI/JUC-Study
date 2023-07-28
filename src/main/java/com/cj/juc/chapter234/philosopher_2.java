package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

public class philosopher_2 {
    public static void main(String[] args) {
        chopstick_r c1 = new chopstick_r();
        chopstick_r c2 = new chopstick_r();
        chopstick_r c3 = new chopstick_r();
        chopstick_r c4 = new chopstick_r();
        chopstick_r c5 = new chopstick_r();

        philosopher_r p1 = new philosopher_r(c1, c2);
        p1.setName("p1");
        p1.start();

        philosopher_r p2 = new philosopher_r(c2, c1);
        p2.setName("p2");
        p2.start();

        philosopher_r p3 = new philosopher_r(c3, c4);
        p3.setName("p3");
        p3.start();

        philosopher_r p4 = new philosopher_r(c4, c5);
        p4.setName("p4");
        p4.start();

        philosopher_r p5 = new philosopher_r(c5, c1);
        p5.setName("p5");
        p5.start();
    }
}

@Slf4j
class philosopher_r extends Thread {
    private chopstick_r left;
    private chopstick_r right;


    public philosopher_r(chopstick_r left, chopstick_r right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            log.debug("{} edting food", Thread.currentThread().getName());
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }

    }
}
    class chopstick_r extends ReentrantLock {
    }
