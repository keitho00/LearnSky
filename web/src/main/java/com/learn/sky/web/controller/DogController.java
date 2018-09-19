package com.learn.sky.web.controller;

import com.learn.sky.web.entity.common.CommonResult;
import com.learn.sky.web.entity.req.DogReq;
import com.learn.sky.web.entity.req.ListReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(value = "list")
    public CommonResult list(@ModelAttribute @Valid ListReq<DogReq> req) {
        return CommonResult.success("");
    }


}
