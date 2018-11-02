package com.learn.sky.redis.Task;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/15 下午3:56
 */
public class Transactional implements Runnable {

    private RedisTemplate redisTemplate;

    private Integer num;

    public Transactional(RedisTemplate redisTemplate, Integer num) {
        this.redisTemplate = redisTemplate;
        this.num = num;
    }

    @Override
    public void run() {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("A", num);
                operations.opsForValue().set("B", num);
                operations.opsForValue().set("C", num);
                operations.opsForValue().set("D", num);
                return  operations.exec();
            }
        });
    }

}
