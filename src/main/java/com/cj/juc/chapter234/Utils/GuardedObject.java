package com.cj.juc.chapter234.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuardedObject {
    public GuardedObject(String id, Object response) {
        this.id = id;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    private String id;
    private Object response;

    public synchronized Object getResponse(long TimeOut)  {
        long PassTime=0;
        while (response==null){

            long start = System.currentTimeMillis();
            TimeOut=TimeOut-PassTime;
            if(TimeOut<=0)
                break;
            log.debug("空值等待{}.....",TimeOut);
            try {
                wait(TimeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PassTime=System.currentTimeMillis()-start;
        }
        return response;
    }

    public synchronized void setResponse(Object response) {
        this.response = response;
        notifyAll();
    }
}
