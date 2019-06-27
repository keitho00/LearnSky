package com.learn.sky.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: JiuBuKong
 * @Date: 2019/6/25 3:42 PM
 */
@Slf4j
@Component
//@DependsOn("pigService")
@Order(2)
public class DogService implements ApplicationRunner {

    public DogService() {
      log.info("DogService construct");
    }

    @PostConstruct
    public void init(){
        log.info("DogService init");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
