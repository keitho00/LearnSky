package com.learn.sky.netty.jeep.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/25 下午8:00
 */
@Data
@NoArgsConstructor
public class Header implements Serializable {

    private static final long serialVersionUID = 5054507448555370415L;

    private Integer length;

    private Integer protoType; //0-hession

    private Integer clazzLength;

    private String clazzName;

}
