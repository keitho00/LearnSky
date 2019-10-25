package com.learn.sky.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.sky.redis.constant.BigDataInnerProperties;
import com.learn.sky.redis.constant.BigDataRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

/**
 * @Author: JiuBuKong
 * @Date: 2019/10/23 3:26 PM
 */
@Slf4j
@Configuration
public class BigDataRedisConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private BigDataRedisProperties redisProperties;
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, BigDataInnerProperties> configMap = redisProperties.getConfigMap();
        if (!MapUtils.isEmpty(configMap)) {
            configMap.forEach((k, v) -> {
                JedisConnectionFactory factory = new JedisConnectionFactory();
                factory.setHostName(v.getHost());
                factory.setPort(v.getPort());
                factory.setTimeout(v.getTimeout());
                factory.setPassword(v.getPassword());
                factory.setDatabase(v.getDatabase());
                factory.afterPropertiesSet();

                //set key serializer
                StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

                Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

                GenericBeanDefinition def = new GenericBeanDefinition();
                def.setBeanClass(RedisTemplate.class);
                MutablePropertyValues mpv = new MutablePropertyValues();
                mpv.addPropertyValue("keySerializer", stringRedisSerializer);
                mpv.addPropertyValue("hashKeySerializer", stringRedisSerializer);
                mpv.addPropertyValue("defaultSerializer", jackson2JsonRedisSerializer);
                mpv.addPropertyValue("connectionFactory", factory);
                def.setPropertyValues(mpv);
                def.setInitMethodName("afterPropertiesSet");
                registry.registerBeanDefinition(k, def);
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.redisProperties = properties();
    }

    private BigDataRedisProperties properties() {
        BigDataRedisProperties properties = new BigDataRedisProperties();
        RelaxedDataBinder binder = new RelaxedDataBinder(properties, BigDataRedisProperties.PREFIX);
        binder.bind(new PropertySourcesPropertyValues(((ConfigurableEnvironment) environment).getPropertySources()));
        return properties;
    }


}
