package com.learn.sky.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: wanghao
 * @Date: 2018/9/18 下午2:08
 */

@SpringBootApplication(
        scanBasePackages = "com.learn.sky",//指定扫描包路径
        scanBasePackageClasses = {})//指定扫描的类
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
