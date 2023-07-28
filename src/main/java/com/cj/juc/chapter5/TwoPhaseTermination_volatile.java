package com.cj.juc.chapter5;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination_volatile {
    private Thread monitor;
    private volatile boolean stop;
    public void start(){
        monitor=new Thread(()->{
            while (true) {
                if (stop)
                    break;
                log.debug("listening is normal interrupt:{}",Thread.currentThread().isInterrupted());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    Thread.currentThread().interrupt();
                };
            }
        },"monitor");
        monitor.start();
    }

    public void stop(){
        stop=true;
        monitor.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination_volatile twoPhaseTermination = new TwoPhaseTermination_volatile();
        twoPhaseTermination.start();
        Thread.sleep(3000);
        twoPhaseTermination.stop();
    }
}
