package com.cj.juc.chapter6;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class Singleton implements Serializable {
    public volatile static Singleton SINGLETON; //保证对象的有序性
    public static Singleton GetInstance(){
        if(SINGLETON==null){                    //这里的SINGLETON没加锁，就可能
            synchronized (Singleton.class){
                if(SINGLETON==null)
                    SINGLETON=new Singleton(); //new Singleton(); JVM中可能会出现先赋值，再实列化问题（用volatile的写屏障）
            }
        }
        return SINGLETON;
    }

    private Object readResolve() throws ObjectStreamException {
        return SINGLETON;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Singleton singleton=Singleton.GetInstance();
                System.out.println(singleton.hashCode());
            }).start();
        }
    }
}
