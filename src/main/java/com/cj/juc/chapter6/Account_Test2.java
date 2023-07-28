package com.cj.juc.chapter6;

import com.cj.juc.chapter6.util.Account_Decimal;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class Account_Test2 {
    public static void main(String[] args) {
        DecimalAccountSafeCas decimalAccountSafeCas = new DecimalAccountSafeCas(new BigDecimal("100000"));
        Account_Decimal.demo(decimalAccountSafeCas);
        DecimalAccountSafeSyn decimalAccountSafeSyn=new DecimalAccountSafeSyn(new BigDecimal("100000"));
        Account_Decimal.demo(decimalAccountSafeSyn);
    }
}
class DecimalAccountSafeCas implements Account_Decimal {
    private AtomicReference<BigDecimal> atomicReference;

    public DecimalAccountSafeCas(BigDecimal bigDecimal) {
        this.atomicReference = new AtomicReference<>(bigDecimal);
    }

    @Override
    public BigDecimal getBalance() {
        return atomicReference.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        atomicReference.getAndUpdate(p->p.subtract(amount));
    }
}

class DecimalAccountSafeSyn implements Account_Decimal {
    private BigDecimal BigDecimal;

    public DecimalAccountSafeSyn(BigDecimal bigDecimal) {
        this.BigDecimal = bigDecimal;
    }

    @Override
    public BigDecimal getBalance() {
        return this.BigDecimal;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        this.BigDecimal=this.BigDecimal.subtract(amount);
    }
}