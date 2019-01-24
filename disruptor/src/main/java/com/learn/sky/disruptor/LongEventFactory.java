package com.learn.sky.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @Author: JiuBuKong
 * @Date: 2019/1/21 下午4:11
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
