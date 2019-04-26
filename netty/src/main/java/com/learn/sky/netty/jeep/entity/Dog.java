package com.learn.sky.netty.jeep.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午10:50
 */
@Data
@NoArgsConstructor
public class Dog implements Serializable {

    private static final long serialVersionUID = 7554618127939192128L;

    private String name;
    private Integer age;
}
