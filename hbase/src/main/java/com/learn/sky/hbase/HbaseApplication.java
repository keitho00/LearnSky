package com.learn.sky.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/20 下午4:59
 */
@SpringBootApplication
        (
                scanBasePackages = "com.learn.sky.hbase",
                exclude = DataSourceAutoConfiguration.class
        )//指定扫描的类
public class HbaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(HbaseApplication.class);
    }
}
