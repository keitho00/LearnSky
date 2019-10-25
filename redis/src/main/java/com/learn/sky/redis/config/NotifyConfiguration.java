package com.learn.sky.redis.config;

import com.learn.sky.redis.notify.MetaUpdateListener;
import com.learn.sky.redis.notify.impl.MessageListenerImpl;
import com.learn.sky.redis.notify.impl.MetaUpdateNotifierImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

@Configuration
public class NotifyConfiguration {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    @ConditionalOnBean(MetaUpdateListener.class)
    public RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory, MetaUpdateListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(listener(listener), topic());
        return container;
    }

    @Bean
    public MetaUpdateNotifierImpl publisher() {
        return new MetaUpdateNotifierImpl(stringRedisTemplate, topic());
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("learn_notify");
    }

    private MessageListenerAdapter listener(MetaUpdateListener listener) {
        return new MessageListenerAdapter(new MessageListenerImpl(listener));
    }

}
