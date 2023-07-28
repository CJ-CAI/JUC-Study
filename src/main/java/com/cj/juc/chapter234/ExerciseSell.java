package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import static java.lang.Thread.sleep;

@Slf4j
public class ExerciseSell {
    static Random random=new Random();
    public static int randomAmount(){
        return random.nextInt(5)-1;
    }
    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow(1000);
        Vector<Integer> sells = new Vector<>();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t=new Thread(()->{
                try {
                    sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int sell = ticketWindow.sell(randomAmount());
                sells.add(sell);
            });
            threads.add(t);
            t.start();
        }
        threads.forEach((t)->{
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.debug("sells:{}",sells.stream().mapToInt(c->c).sum());
        log.debug("remainder count:{}",ticketWindow.getCount());
    }
}

class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else return 0;
    }
}