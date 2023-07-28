package com.cj.juc.chapter6;

import com.cj.juc.chapter6.util.Unsafe_C;
import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Unsafe_1 {
    public static void main(String[] args) throws NoSuchFieldException {
        Unsafe unsafe= Unsafe_C.getUnsafe();

        Field id = Student.class.getDeclaredField("id");
        Field name = Student.class.getDeclaredField("name");

        long idOffset = unsafe.objectFieldOffset(id);
        long nameOffset = unsafe.objectFieldOffset(name);

        Student student = new Student();

        unsafe.compareAndSwapInt(student,idOffset,0,22);
        unsafe.compareAndSwapObject(student,nameOffset,null,"Butler");
        System.out.println(student.toString());

    }
}
@Data
class Student {
    volatile int id;
    volatile String name;
}

