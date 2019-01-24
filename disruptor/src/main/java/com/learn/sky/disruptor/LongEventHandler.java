package com.learn.sky.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: JiuBuKong
 * @Date: 2019/1/21 下午4:14
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("Event: " + longEvent);
    }
}
