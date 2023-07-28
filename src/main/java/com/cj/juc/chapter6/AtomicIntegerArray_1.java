package com.cj.juc.chapter6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicIntegerArray_1 {
    public static void main(String[] args) {
        Array_Test.demo(
                ()->new int[10],
                (array)->array.length,
                (array,index)->array[index]++,
                array-> System.out.println(Arrays.toString(array))
        );

        Array_Test.demo(
                ()->new AtomicIntegerArray(10),
                (array)->array.length(),
                (array,index)->array.getAndIncrement(index),
                array-> System.out.println(array)
        );
    }
}

class Array_Test {
    public static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T, Integer> lengthFun,
            BiConsumer<T, Integer> putConsumer,
            Consumer<T> printConsumer) {

        ArrayList<Thread> ts = new ArrayList<>();
        T array = arraySupplier.get();
        Integer length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            ts.add(new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    putConsumer.accept(array,j%length);
                }
            }));
        }

        ts.forEach(t->t.start());

        ts.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printConsumer.accept(array);
    }

}
