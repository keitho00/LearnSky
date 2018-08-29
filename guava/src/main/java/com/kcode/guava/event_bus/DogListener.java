package com.kcode.guava.event_bus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.Data;

/**
 * @Author: wanghao
 * @Date: 2018/8/10 下午3:57
 */
@Data
public class DogListener {

    private Dog dog;

    @Subscribe
    @AllowConcurrentEvents
    public void listener(Dog dog) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.dog = dog;
        System.out.println("dog name:" + dog.getName() + " age:" + dog.getAge());
    }
}
