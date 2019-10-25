package com.learn.sky.redis.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BigDataInnerProperties {

    private String host;
    private int port;
    private int timeout;
    private int database;
    private String password;
}
