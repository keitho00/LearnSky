package com.learn.sky.redis.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/15 下午2:25
 */
@RestController
@RequestMapping("redis")
public class IncreaseController {

    private static final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);

    @Resource
    RedisTemplate redisTemplate;


    @GetMapping("/increase")
    public void increase() {
        newFixedThreadPool.execute(new Increase());
        newFixedThreadPool.execute(new Increase());
        try {
            TimeUnit.MINUTES.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Increase implements Runnable {

        @Override
        public void run() {
            redisTemplate.opsForValue().increment("key-1", 1L);
            System.out.println(redisTemplate.opsForValue().get("key-1"));
        }
    }


}
