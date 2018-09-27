package com.learn.sky.web.controller;

import com.learn.sky.web.entity.common.CommonResult;
import com.learn.sky.web.entity.req.DogReq;
import com.learn.sky.web.entity.req.ListReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: JiuBuKong
 * @Date: 2018/9/19 下午5:28
 */
@RestController
@RequestMapping("/dog")
@CacheConfig(cacheNames = "dog")
@Slf4j
public class DogController {

    /**
     * list校验 example
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/list")
    @Cacheable//用缓存的时候一定要注意查询失败没有数据时的空对象填充
    public CommonResult list(@RequestBody @Valid ListReq<DogReq> req) {
        log.info("dog list");

        return CommonResult.success("");
    }


}
