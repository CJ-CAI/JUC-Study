package com.cj.juc.chapter6;

import com.cj.juc.chapter6.util.Account;

import java.util.concurrent.atomic.AtomicInteger;

public class Account_Test {
    public static void main(String[] args) {
        account_unSafe a = new account_unSafe(100000);
        account_cas cas = new account_cas(100000);
        Account.demo(a);
        Account.demo(cas);
    }
}

class account_unSafe implements Account {
    private Integer Balance;

    public account_unSafe(Integer balance) {
        Balance = balance;
    }

    @Override
    public Integer getBalance() {
        return Balance;
    }

    @Override
    public void withdraw(Integer amount) {
        synchronized (account_unSafe.class) {
            Balance -= amount;
        }
    }
}

class account_cas implements Account {
    private AtomicInteger Balance;

    public account_cas(Integer balance) {
        this.Balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return Balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        /*while (true) {
            Integer prev = this.Balance.get();
            Integer new_value = prev - amount;
            if(Balance.compareAndSet(prev,new_value))
                break;
        }*/

        Balance.addAndGet(-amount);
    }
}