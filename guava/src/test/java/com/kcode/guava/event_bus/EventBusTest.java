package com.kcode.guava.event_bus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wanghao
 * @Date: 2018/8/10 下午4:05
 */
public class EventBusTest {

    ExecutorService executor = Executors.newFixedThreadPool(4);


    @Test
    public void simple() {
        //简历消息总线
        EventBus eventBus = new EventBus();
        //注册消费者
        eventBus.register(new AnimalListener());
        Dog dog = new Dog();
        dog.setName("小白");
        dog.setAge(11);
        //发布消息
        eventBus.post(dog);
    }

    @Test
    public void asyncTest() {
        //简历消息总线
        EventBus eventBus = new AsyncEventBus(executor);
        //注册消费者
        eventBus.register(new AnimalListener());
        eventBus.register(new DogListener());
        Dog dog = new Dog();
        dog.setName("小白");
        dog.setAge(11);
        long start = System.currentTimeMillis();
        //发布消息
        eventBus.post(dog);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void currency() {


    }

    @Test
    public void syncTest() {
        //简历消息总线
        EventBus eventBus = new EventBus();
        //注册消费者
        eventBus.register(new AnimalListener());
        eventBus.register(new DogListener());
        Dog dog = new Dog();
        dog.setName("小白");
        dog.setAge(11);
        long start = System.currentTimeMillis();
        //发布消息
        eventBus.post(dog);
        System.out.println(System.currentTimeMillis() - start);
    }
}
