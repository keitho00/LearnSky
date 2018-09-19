package com.learn.sky.web.controller;

import com.learn.sky.web.entity.common.CommonResult;
import com.learn.sky.web.entity.req.DogReq;
import com.learn.sky.web.entity.req.ListReq;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: JiuBuKong
 * @Date: 2018/9/19 下午5:28
 */
@RestController
@RequestMapping("/dog")
public class DogController {

    /**
     * list校验 example
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/list")
    public CommonResult list(@RequestBody @Valid ListReq<DogReq> req) {
        return CommonResult.success("");
    }


}
