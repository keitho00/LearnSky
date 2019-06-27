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
@Order(1)
public class PigService implements ApplicationRunner {

    public PigService() {
        log.info("PigService construct");
    }


    @PostConstruct
    public void init(){
        log.info("PigService init");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
