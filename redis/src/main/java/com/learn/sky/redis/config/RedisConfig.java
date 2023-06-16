package com.learn.sky.redis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Spring redis 和 cache的配置文件
 * 一旦选用自己去配置RedisTemplate后就只能自己去配置CacheManager
 * RedisAutoConfiguration
 * RedisCacheConfiguration
 *
 * @Author: JiuBuKong
 * @Date: 2018/9/25 下午4:32
 */
@Configuration
@EnableConfigurationProperties(value = RedisProperties.class)
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Resource
    JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisProperties.getHost());
        factory.setPort(redisProperties.getPort());
        factory.setTimeout(redisProperties.getTimeout());
        factory.setPassword(redisProperties.getPassword());
        factory.setDatabase(redisProperties.getDatabase());
        return factory;
    }


    @Bean("redisTemplate")
    @Primary
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean(name = "checkandset")
    public String checkandset() throws IOException {
        return readScript("lua/checkandset.lua");
    }
    @Bean(name = "incrscript")
    public String incrscript() throws IOException {
        return readScript("lua/incrscript.lua");
    }

    private String readScript(String url) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream(), "UTF-8"));
        String s; // 依次循环，至到读的值为空
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(s);
            sb.append(" ");
        }
        reader.close();
        return sb.toString().substring(0,sb.length()-1);
    }


}
