package com.learn.sky.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/20 下午4:59
 */
@SpringBootApplication
        (
                scanBasePackages = "com.learn.sky.hbase"
        )//指定扫描的类
public class HbaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(HbaseApplication.class);
    }
}
