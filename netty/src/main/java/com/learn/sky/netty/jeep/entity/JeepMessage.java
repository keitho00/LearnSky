package com.learn.sky.netty.jeep.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2019/4/25 下午7:43
 */
@Data
@NoArgsConstructor
public class JeepMessage implements Serializable {

    private static final long serialVersionUID = 494666028717580746L;

    private Header header;

    private Object body;
}
