package com.cj.juc.chapter8.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

@Slf4j
public class hunger {
    public static void main(String[] args) throws InterruptedException {
//        hungry();//因为只有两个核心线程，出现线程饥饿
        classify();
    }

    private static void hungry() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                log.debug("取菜.....");
                Future<Boolean> dish = executorService.submit(() -> {
                    log.debug("做菜");
                    return true;
                });
                try {
                    log.debug("上菜{}", dish.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void classify() throws InterruptedException {
        ExecutorService cook_dish = Executors.newFixedThreadPool(1);
        ExecutorService deliver_dish = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            cook_dish.execute(() -> {
                log.debug("取菜.....");
                Future<Boolean> dish = deliver_dish.submit(() -> {
                    log.debug("做菜");
                    return true;
                });
                try {
                    log.debug("上菜{}", dish.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
