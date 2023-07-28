package com.cj.juc.chapter8.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

@Slf4j
public class Constructor_test2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor) executorService1;
        ArrayList arrayList = new ArrayList();
        threadPoolExecutor.setCorePoolSize(10);

       /* Executors.newSingleThreadExecutor() 线程个数始终为1，不能修改
        FinalizableDelegatedExecutorService 应用的是装饰器模式，只对外暴露了 ExecutorService 接口，因
        此不能调用 ThreadPoolExecutor 中特有的方法
        Executors.newFixedThreadPool(1) 初始时为1，以后还可以修改
        对外暴露的是 ThreadPoolExecutor 对象，可以强转后调用 setCorePoolSize 等方法进行修改*/

       /* Thread thread = new Thread();
        Future<?> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 2+3;
            }
        });

        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            return 2 + 2;
        });
        new Thread(futureTask).start();

        System.out.println(futureTask.get());

        System.out.println(future);
        System.out.println(future.get());*/

        Collection<Callable<String>> callables= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number=i;
            callables.add(()->{
                log.debug("Number:{}......",number);
                sleep(2000);
                return String.valueOf(number);
            });
        }
     /*   List<Future<String>> futures = executorService.invokeAll(callables);
        futures.forEach(f-> {
            try {
                log.debug("result:{}", f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });*/

        String future = executorService.invokeAny(callables,1,TimeUnit.SECONDS);
        System.out.println(future);
        executorService.shutdown();
        executorService.shutdownNow();

    }
}
