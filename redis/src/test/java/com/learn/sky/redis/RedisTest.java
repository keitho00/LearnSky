package com.learn.sky.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.sky.redis.Task.Increase;
import com.learn.sky.redis.Task.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/15 下午3:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class RedisTest {

    private static final ScheduledExecutorService newFixedThreadPool = Executors.newScheduledThreadPool(4);

    @Resource
    RedisTemplate redisTemplate;


    @Test
    public void increase() {

        redisTemplate.opsForValue().set("key-1", 0);
        newFixedThreadPool.scheduleAtFixedRate(new Increase(redisTemplate), 0, 1, TimeUnit.SECONDS);
        newFixedThreadPool.scheduleAtFixedRate(new Increase(redisTemplate), 0, 1, TimeUnit.SECONDS);
        try {
            TimeUnit.MINUTES.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void transactional() {
        newFixedThreadPool.scheduleAtFixedRate(new Transactional(redisTemplate, 1), 0, 100, TimeUnit.MILLISECONDS);
        newFixedThreadPool.scheduleAtFixedRate(new Transactional(redisTemplate, 2), 0, 101, TimeUnit.MILLISECONDS);
        newFixedThreadPool.scheduleAtFixedRate(new Transactional(redisTemplate, 3), 0, 102, TimeUnit.MILLISECONDS);
        newFixedThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("======start========");
                redisTemplate.watch("A");
                redisTemplate.watch("B");
                redisTemplate.watch("C");
                redisTemplate.multi();
                System.out.println("======middle========");
                System.out.println(redisTemplate.opsForValue().get("A"));
                System.out.println(redisTemplate.opsForValue().get("B"));
                System.out.println(redisTemplate.opsForValue().get("C"));
                List<Object> objectList = redisTemplate.exec();
                ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
                try {
                    System.out.println(objectMapper.writeValueAsString(objectList));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
        try {
            TimeUnit.MINUTES.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 管道模式下能够提高批量处理数据的性能
     * pipeline: 810 ms
     * no pipeline: 8236 ms
     */
    @Test
    public void pipeline() {

        //pipeline
        Long start = System.currentTimeMillis();
        List<Object> objectList = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    operations.boundValueOps("key-1").increment(1);
                    operations.boundValueOps("key-1").get();
                }
                return null;
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("pipeline: " + (end - start));


        //noPipeline
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForValue().increment("key-1", 1);
            redisTemplate.opsForValue().get("key-1");
        }
        end = System.currentTimeMillis();
        System.out.println("no pipeline: " + (end - start));

    }


    @Test
    public void noPipeline() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForValue().increment("key-1", 1);
            redisTemplate.opsForValue().get("key-1");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
