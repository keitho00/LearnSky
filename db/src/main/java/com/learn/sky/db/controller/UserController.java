package com.learn.sky.db.controller;

import com.learn.sky.db.dao.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/2 下午2:12
 */
@RestController
@RequestMapping("user")
public class UserController {


    @Resource
    UserMapper userMapper;


}
