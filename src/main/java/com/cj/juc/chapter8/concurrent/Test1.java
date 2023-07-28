package com.cj.juc.chapter8.concurrent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author CJ_CA
 */
public class Test1{
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> maps = new ConcurrentHashMap<>(10);
        maps.put("1",1);
        Integer integer = maps.get("1");
        maps.computeIfAbsent("1",(v)-> Integer.valueOf(v+=2));
        maps.size();

        HashMap<String, Integer> hashMap = new HashMap<>(10);
        hashMap.get("");
    }
}
