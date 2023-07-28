package com.cj.juc.chapter8.CustomThreadPool;


import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Slf4j
public class ThreadPool {
    private BlockingQueue<Runnable> blockingQueue;
    private HashSet<Worker> Workers=new HashSet<Worker>();
    private int CoreSize;
    private long timeOut;
    private TimeUnit timeUnit;
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize,int queueCapacity, long timeOut, TimeUnit timeUnit, RejectPolicy<Runnable> rejectPolicy) {
        CoreSize = coreSize;
        timeOut = timeOut;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
        this.blockingQueue=new BlockingQueue<>(queueCapacity);
    }

    public void execute(Runnable task){
        if(Workers.size()<CoreSize){
            Worker worker = new Worker(task);
            Workers.add(worker);
            log.debug("新增 worker{}, {}", worker, task);
            worker.start();
        }else {
            blockingQueue.tryPut(rejectPolicy,task);
        }
    }

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task !=null || (task=blockingQueue.pull_time(timeOut,timeUnit))!=null){
                try {
                    log.debug("running.....{}",task);
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    task=null;
                }
            }
        }
    }
}
