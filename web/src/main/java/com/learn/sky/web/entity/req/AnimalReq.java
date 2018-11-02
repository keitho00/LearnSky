package com.learn.sky.web.entity.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/12 下午4:41
 */
@Data
@NoArgsConstructor
public class AnimalReq implements Serializable {
    private static final long serialVersionUID = -2264087074434530398L;

    private int type;
}
