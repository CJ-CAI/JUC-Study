package com.cj.juc.chapter8.others;

import java.util.concurrent.*;

/**
 * @author CJ_CA
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        ConcurrentLinkedQueue<Integer> concurrentLinkedDeque = new ConcurrentLinkedQueue<Integer>();
        CopyOnWriteArrayList<Integer> integers = new CopyOnWriteArrayList<>();
        integers.add(12);
        integers.get(2);
        integers.set(1,2);
        concurrentLinkedDeque.add(10);
        arrayBlockingQueue.add(1);
        for (int i = 0; i < 100; i++) {
            int number=i;
            new Thread(()->{
                blockingQueue.add(number);
                try {
                    blockingQueue.put(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                blockingQueue.remove();
            }).start();
        }
        blockingQueue.forEach(word-> System.out.print(word+" "));
    }
}
