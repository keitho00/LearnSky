package com.learn.sky.guava.EventBus;

/**
 * @Author: wanghao
 * @Date: 2018/4/23 下午5:06
 */
public class OrderEvent {
    private String message;

    public OrderEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
