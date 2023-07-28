package com.cj.juc.chapter234.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public
class Mail {
    private String Message;

    public Mail(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
