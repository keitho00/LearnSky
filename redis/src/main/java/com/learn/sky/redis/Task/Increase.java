package com.learn.sky.redis.Task;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/15 下午3:04
 */
public class Increase implements Runnable {

    private RedisTemplate redisTemplate;

    public Increase(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        Long count = redisTemplate.opsForValue().increment("key-1", 1L);
        System.out.println(count);
    }
}