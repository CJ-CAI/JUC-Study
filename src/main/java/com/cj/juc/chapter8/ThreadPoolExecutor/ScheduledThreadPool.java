package com.cj.juc.chapter8.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.*;

@Slf4j
public class ScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
     /*   Future<?> future = scheduledExecutorService.submit(() -> {
            int result = 10 / 0;
            log.debug("running.....");
        });
        System.out.println(future.get());*/

        LocalTime now = LocalTime.now();
        log.debug("now:{}",now);
        LocalTime Next_Time = now.withMinute(46);
        log.debug("NextTime:{}",Next_Time);
        long start = Duration.between(now, Next_Time).toMillis();

        scheduledExecutorService.scheduleWithFixedDelay(()->{
            log.debug("running...");
        },start,1, TimeUnit.MINUTES);

    }
}
