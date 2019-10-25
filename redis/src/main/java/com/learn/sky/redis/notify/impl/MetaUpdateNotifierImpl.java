package com.learn.sky.redis.notify.impl;

import com.learn.sky.redis.notify.MetaUpdateNotifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * @author Lixiaochun
 */
public class MetaUpdateNotifierImpl implements MetaUpdateNotifier {

    private StringRedisTemplate redisTemplate;
    private ChannelTopic topic;

    public MetaUpdateNotifierImpl(StringRedisTemplate redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void notifyUpdate(String context) {
        redisTemplate.convertAndSend(topic.getTopic(), context);
    }

}
