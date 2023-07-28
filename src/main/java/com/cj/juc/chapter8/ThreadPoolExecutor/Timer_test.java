package com.cj.juc.chapter8.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;
@Slf4j
public class Timer_test {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                try {
                    int s=10/0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("task1 running....");
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task2 running....");
            }
        };
        log.debug("start....");
        timer.schedule(task1,1000);
        timer.schedule(task2,1000);
    }
}
