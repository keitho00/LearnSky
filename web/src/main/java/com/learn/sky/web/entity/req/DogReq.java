package com.learn.sky.web.entity.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: JiuBuKong
 * @Date: 2018/9/19 下午5:22
 */
@Data
@NoArgsConstructor
public class DogReq extends AnimalReq implements Serializable {
    private static final long serialVersionUID = 1087195022605271513L;

    @NotNull
    private String name;

    @NotNull
    private Integer age;
}
