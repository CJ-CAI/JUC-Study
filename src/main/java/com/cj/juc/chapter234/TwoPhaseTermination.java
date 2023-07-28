package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {
    private Thread monitor;
    public void start(){
        monitor=new Thread(()->{
            while (true) {
                if (Thread.currentThread().isInterrupted())
                    break;
                log.debug("listening is normal interrupt:{}",Thread.currentThread().isInterrupted());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                   e.printStackTrace();
                    Thread.currentThread().interrupt();
                };
            }
        },"monitor");
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        Thread.sleep(3000);
        twoPhaseTermination.stop();
    }
}
