package com.learn.sky.redis.notify.impl;

import com.learn.sky.redis.notify.MetaUpdateListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Slf4j
public class MessageListenerImpl implements MessageListener {

    private MetaUpdateListener listener;

    public MessageListenerImpl(MetaUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.debug("message:{}", message);
        listener.onUpdate(new String(message.getBody()));
    }

}
