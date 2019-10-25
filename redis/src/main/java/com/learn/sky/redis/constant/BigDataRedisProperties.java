package com.learn.sky.redis.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class BigDataRedisProperties {

    public static final String PREFIX = "bigData.redis";

    private Map<String, BigDataInnerProperties> configMap;
}
