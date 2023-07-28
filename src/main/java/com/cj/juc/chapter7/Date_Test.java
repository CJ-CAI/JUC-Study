package com.cj.juc.chapter7;

import jdk.vm.ci.meta.Local;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Slf4j
public class Date_Test {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    log.debug("{}",simpleDateFormat.parse("2023-7-18"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate parse = dateTimeFormatter.parse("2023-07-18", LocalDate::from);
                log.debug("{}", parse);
            }).start();
        }
    }
}
