package com.learn.sky.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date: 2018/9/18 下午2:08
 */
//@EnableAsync
@SpringBootApplication
        (
        scanBasePackages = "com.learn.sky"
//        , scanBasePackageClasses = {RedisAutoConfiguration.class}//指定扫描包路径 不能为空
)//指定扫描的类
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
