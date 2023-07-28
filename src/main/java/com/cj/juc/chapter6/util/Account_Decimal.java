package com.cj.juc.chapter6.util;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface Account_Decimal {
    BigDecimal getBalance();
    void withdraw(BigDecimal amount);
    static  void demo(Account_Decimal account){
        ArrayList<Thread> threads = new ArrayList<>();
        long start=System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            threads.add(new Thread(()->{
                account.withdraw(new BigDecimal("10"));
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        System.out.println(account.getBalance()+"cost time :"+(System.currentTimeMillis()-start));
    }
}
