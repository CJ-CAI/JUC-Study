package com.cj.juc.chapter8.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;

public class ForkJoinPool_Test {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        System.out.println(forkJoinPool.invoke(new task_c(1, 5)));
    }
}
@Slf4j
class task_c extends RecursiveTask<Integer>{

    int start;
    int end;

    public task_c(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    public String toString() {
        return "{" + start + "," + end + '}';
    }

    @Override
    protected Integer compute() {
        if (start==end){
            log.debug("join(){}={}",start,end);
            return start;
        }
        if(end-start==1){
            log.debug("join(){}+{}",start,end);
            return start+end;
        }
        int mid=(start+end)/2;
        task_c task_left = new task_c(start,mid);
        task_left.fork();

        task_c task_right = new task_c(mid+1,end);
        task_right.fork();

        log.debug("fork() {} + {} = ?", task_left,task_right);
        int result=task_left.join()+task_right.join();
        log.debug("fork() {} + {} = {}", task_left,task_right,result);
        return result;
    }
}
