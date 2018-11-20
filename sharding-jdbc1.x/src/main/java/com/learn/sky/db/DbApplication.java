package com.learn.sky.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: wanghao
 * @Date: 2018/9/18 下午2:08
 */

@SpringBootApplication
        (
                scanBasePackages = "com.learn.sky",
                exclude = {DataSourceAutoConfiguration.class}
        )//指定扫描的类
public class DbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class);
    }

}
