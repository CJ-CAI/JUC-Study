package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ThreadSafe {
    static final int THREAD_NUMBER=2;
    static final int LOOP_NUMBER=200;

    public static void main(String[] args) {
        ThreadUnsafe threadUnsafe = new ThreadUnsafeSon();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                threadUnsafe.method(LOOP_NUMBER);
            }).start();
        }
    }
}
class ThreadUnsafe{

    public void method(int loop_number){
        ArrayList<String> list=new ArrayList<>();
        for (int i = 0; i < loop_number; i++) {
            method1(list);
            method2(list);
        }
    }
    private void method1(ArrayList<String> list){
        list.add("1");
    }
    public void method2(ArrayList<String> list){
        list.remove(0);
    }
}
class ThreadUnsafeSon extends ThreadUnsafe{
    @Override
    public void method2(ArrayList<String> list) {
        new Thread(()->{
            list.remove(0);
        }).start();
    }
}
//多个线程 共享资源 操作共享资源