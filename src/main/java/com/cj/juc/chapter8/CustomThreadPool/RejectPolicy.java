package com.cj.juc.chapter8.CustomThreadPool;



@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}
