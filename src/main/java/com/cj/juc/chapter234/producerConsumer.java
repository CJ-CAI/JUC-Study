package com.cj.juc.chapter234;

import com.cj.juc.chapter234.Utils.Mail;
import com.cj.juc.chapter234.Utils.MessageQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static java.lang.Thread.sleep;
@Slf4j
public class producerConsumer {

    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(3);
        new Thread(()->{
            Mail mail = messageQueue.getMail();
            log.debug(mail.getMessage());
        }).start();
        sleep(1000);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                messageQueue.addMail(new Mail(UUID.randomUUID().toString()));
            },String.valueOf(i)).start();
        }

        sleep(1000);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Mail mail = messageQueue.getMail();
                log.debug(mail.getMessage());
            },String.valueOf(i)).start();
        }
    }
}
