package com.cj.juc.chapter6;

import com.cj.juc.chapter6.util.Account;
import com.cj.juc.chapter6.util.Unsafe_C;
import sun.misc.Unsafe;

public class AtomicData {
    private int data;
    static final Unsafe unsafe=Unsafe_C.getUnsafe();
    static final long DATA_OFFSET;

    static{
        try {
            DATA_OFFSET=unsafe.objectFieldOffset(AtomicData.class.getDeclaredField("data"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public AtomicData(int data) {
        this.data = data;
    }

    public void decrease(int amount){
        int oldValue;
        while (true){
            oldValue=data;
            if (unsafe.compareAndSwapInt(this,this.DATA_OFFSET,oldValue,oldValue-amount)){
                break;
            }
        }
    }

    public int getData() {
        return data;
    }

    public static void main(String[] args) {
        Account.demo(new Account() {
            AtomicData atomicData=new AtomicData(100000);
            @Override
            public Integer getBalance() {
                return atomicData.getData();
            }

            @Override
            public void withdraw(Integer amount) {
                atomicData.decrease(amount);
            }
        });
    }
}
