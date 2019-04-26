package com.learn.sky.netty.jeep.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/26 上午10:49
 */
@Data
@NoArgsConstructor
public class Cat implements Serializable {

    private static final long serialVersionUID = 3117095453018896024L;

    private String name;
    private Integer age;
}
