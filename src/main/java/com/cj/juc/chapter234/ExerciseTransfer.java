package com.cj.juc.chapter234;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class ExerciseTransfer {
    static Random random = new Random();

    public static int randomAmount() {
        return random.nextInt(100) - 1;
    }

    public static void main(String[] args) {
        Account accountA = new Account(1000);
        Account accountB = new Account(1000);
        Thread threadA = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                accountA.transfer(accountB, randomAmount());
            }
        });
        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                accountB.transfer(accountA, randomAmount());
            }
        });
        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("AccountA:{} AccountB: {}", accountA.getMoney(), accountB.getMoney());
    }
}

class Account {
    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void transfer(Account target, int amount) {
        synchronized (Account.class) {
            if (this.money > amount) {
                this.setMoney(this.getMoney() - amount);
                target.setMoney(target.getMoney() + amount);
            }
        }
    }

}
