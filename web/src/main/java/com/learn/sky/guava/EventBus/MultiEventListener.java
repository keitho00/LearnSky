package com.learn.sky.guava.EventBus;

import com.google.common.eventbus.Subscribe;

/**
 * @Author: wanghao
 * @Date: 2018/4/23 下午5:08
 */
public class MultiEventListener {
    @Subscribe
    public void listen(OrderEvent event){
        System.out.println("receive msg: "+event.getMessage());
    }

    @Subscribe
    public void listen(String message){
        System.out.println("receive msg: "+message);
    }
}
