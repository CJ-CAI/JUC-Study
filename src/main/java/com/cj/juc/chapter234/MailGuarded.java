package com.cj.juc.chapter234;

import com.cj.juc.chapter234.Utils.GuardedObject;
import com.cj.juc.chapter234.Utils.Mail;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class MailGuarded {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new People().start();
        }
        sleep(1000);

        Set<String> ids = MailBox.getIDS();
        for (String id : ids) {
            new Postman(id,"CJ").start();
        }
    }
}
@Slf4j
class People extends Thread{
    @Override
    public void run() {
        GuardedObject guardedObject = new GuardedObject(UUID.randomUUID().toString(),null);
        MailBox.addMail(guardedObject);
        Mail response = (Mail)guardedObject.getResponse(5000);
        log.debug("收到信息id:{} Message:{}",guardedObject.getId(),response.getMessage());
        MailBox.addMail(guardedObject);
    }
}
@Slf4j
class Postman extends Thread{
    private String id;
    private String Message;

    Postman(String id, String Message){
        this.id=id;
        this.Message=Message;
    }
    @Override
    public void run() {
        GuardedObject guardedObject = MailBox.get(id);
        log.debug("投送消息");
        guardedObject.setResponse(new Mail(Thread.currentThread().getName()+":"+Message));
    }
}
class MailBox{
    private static Hashtable<String,GuardedObject> MailList=new Hashtable<>(1);

    public  static  void  addMail(GuardedObject guardedObject){
        MailList.put(guardedObject.getId(),guardedObject);
    }
    public  static GuardedObject get(String id){
       return MailList.remove(id);
    }

    public  static java.util.Set<String> getIDS(){
        return MailList.keySet();
    }
}
