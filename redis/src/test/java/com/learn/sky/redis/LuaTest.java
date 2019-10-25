package com.learn.sky.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @Author: JiuBuKong
 * @Date: 2019/10/9 2:08 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Slf4j
public class LuaTest {

    @Resource(name = "checkandset")
    DefaultRedisScript<Boolean> redisScript;

    @Resource(name = "incrscript")
    DefaultRedisScript<String> incrScript;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void testRedisLua() {
        String key = "testredislua";

        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, "hahaha");
        String s = (String) redisTemplate.opsForValue().get(key);
        log.info("1=======" + s);
        redisTemplate.execute(redisScript, Collections.singletonList(key), "hahaha", "3333");
        s = (String) redisTemplate.opsForValue().get(key);
        log.info("2=======" + s);
    }

    @Test
    public void testRedisLua2() {
        String key = "incrscript";
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, "1");
        String res = (String) stringRedisTemplate.execute(incrScript, Collections.singletonList(key), "1", "10", "1000");
        log.info("res:" + res);
    }
}
