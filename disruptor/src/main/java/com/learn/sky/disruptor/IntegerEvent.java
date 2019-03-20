package com.learn.sky.disruptor;

/**
 * @Author: JiuBuKong
 * @Date: 2019/1/21 下午4:11
 */
public class IntegerEvent {

    private Integer value;

    public void set(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
