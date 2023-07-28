package com.cj.juc.chapter6;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LongAdder_1 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            demo(()->new AtomicLong(),(adder)->adder.incrementAndGet());
            demo(()->new LongAdder(),longAdder -> longAdder.increment());
            System.out.println();
        }
    }

    private static <T> void demo(Supplier<T> adderSupper, Consumer<T> action){
          T adder = adderSupper.get();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(()->{
                for (int j = 0; j < 50000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long s = System.nanoTime();
        threads.forEach(thread -> thread.start());
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end=System.nanoTime()-s;
        System.out.println("cost: "+end/1000_000);


    }
}
