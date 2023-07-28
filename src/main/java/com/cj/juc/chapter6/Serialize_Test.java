package com.cj.juc.chapter6;

import java.io.*;

public class Serialize_Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Singleton singleton = Singleton.GetInstance();
        System.out.println(singleton.hashCode());
//        String abcd = new String("abcd");
//        System.out.println(abcd.hashCode());
        ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(".\\singleton.txt"));
        objectOutput.writeObject(singleton);
        objectOutput.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(".\\singleton.txt"));
        Object o = (Singleton)objectInputStream.readObject();
        System.out.println(o.hashCode());
        objectInputStream.close();
    }
}


