package com.cj.juc.chapter234;

public class StarkFrames {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            method1(2);
        });
        thread.setName("t1");
        thread.start();
        method1(2);
    }

    public static void method1(int x){
//

        int y=x+1;
        Object o = method2();
        System.out.println(o);
    }
    public static Object method2(){
        Object o = new Object();
        return o;
    }
}
