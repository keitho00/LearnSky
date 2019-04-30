package com.learn.sky.web.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * 此处只是样例  并不能直接使用
 * 主要提供一个properties获取mq属性，Config生产生产者、消费者的类
 * @Date: 2018/8/27 下午4:38
 */
@Slf4j
@Configuration
public class RocketMQConfig {
    @Resource
    private MQProducerProperties producerProperties;

    @Resource
    private MQConsumerProperties consumerProperties;

    //todo 完善使其可用
    @Bean
    public DefaultMQPushConsumer consumer() throws MQClientException {
        //groupName 同工单配置时的consumer group name保持一致，如果一个server group id下有多个消费组，则创建多个consumer实例
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerProperties.getGroupName());//设置etcdAddrs，各环境etcdAddrs参看附录

        consumer.setNamesrvAddr(consumerProperties.getEtcdAddress());
        //clusterId 同工单申请时的clusterId保持一致

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //设置consumer所订阅的Topic和Tag，*代表全部的Tag
        consumer.subscribe("TopicTest", "*");

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
            System.out.println(context);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        return consumer;
    }


    @Bean
    public DefaultMQProducer buildProducer() throws MQClientException {
        //和TopicName保持一致
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(producerProperties.getTopicName());
        //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        //NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码里
        defaultMQProducer.setNamesrvAddr("118.24.172.27:9876");
        //重试三次
        defaultMQProducer.setRetryTimesWhenSendFailed(3);
        defaultMQProducer.start();
        try {
            SendResult sendResult = defaultMQProducer.send(new Message(producerProperties.getTopicName(), "tag", "body".getBytes()));
        } catch (Exception e) {
            //print log
            e.printStackTrace();
        }
        return defaultMQProducer;
    }

    @Component
    @Data
    @ConfigurationProperties(prefix = "rocket.producer")
    public static class MQProducerProperties {

        private String etcdAddress;

        private int clusterId;

        private int groupId;

        private String topicName;

        private int serviceType;
    }

    @Component
    @Data
    @ConfigurationProperties(prefix = "rocket.consumer")
    public static class MQConsumerProperties {

        private String etcdAddress;

        private int clusterId;

        private int groupId;

        private String groupName;

        private int serviceType;
    }
}
