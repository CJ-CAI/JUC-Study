package com.cj.juc.chapter6;

import java.io.*;

// 原始对象
class Person implements Serializable {

    private String name;

    private static Person person=new Person("Peter");

    public static Person getPeron(){
        return person;
    }
    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Object readResolve() throws ObjectStreamException {
        return person;
    }

    public Person() {
    }
}

// 序列化和反序列化
public class SerializationExample {
    public static void main(String[] args) {
//        Person originalPerson = Person.getPeron();
//
//        // 序列化对象
//        try (FileOutputStream fos = new FileOutputStream("person.ser");
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(originalPerson);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 反序列化对象
        try (FileInputStream fis = new FileInputStream("person.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Person deserializedPerson = (Person) ois.readObject();
            System.out.println(deserializedPerson.getName());  // 输出 "Alice"
//            System.out.println(originalPerson == deserializedPerson);  // 输出 "false"
//            System.out.println(originalPerson.hashCode());
            System.out.println(deserializedPerson.hashCode());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
