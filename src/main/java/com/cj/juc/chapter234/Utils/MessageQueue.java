package com.cj.juc.chapter234.Utils;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
@Slf4j
public class MessageQueue {
    private LinkedList<Mail> mailLinkedList=new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }
    public Mail getMail(){
        synchronized (mailLinkedList){
            while (mailLinkedList.isEmpty()){
                try {
                    log.debug("队列为空....等待消息");
                    mailLinkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Mail mail = mailLinkedList.removeFirst();
            mailLinkedList.notifyAll();
            return mail;
        }
    }
    public void addMail(Mail mail){
        synchronized (mailLinkedList){
            while (mailLinkedList.size()==capacity){
                try {
                    log.debug("队列已满....等待消费者消费");
                    mailLinkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mailLinkedList.add(mail);
            mailLinkedList.notifyAll();
        }
    }
}
