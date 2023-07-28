package com.cj.juc.chapter8.others;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Thread.sleep;

/**
 * @author CJ_CA
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        //遍历的是旧引用
        Iterator<Integer> iter = list.iterator();
        new Thread(() -> {
            list.remove(0);
            System.out.println(list);
        }).start();
        sleep(3000);
        //此时的iter是删除之前的引用，因为CopyWriteList修改使用的是复制修改
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
