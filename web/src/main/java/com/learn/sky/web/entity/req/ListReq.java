package com.learn.sky.web.entity.req;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * list的参数校验结构
 *
 * @Author: JiuBuKong
 * @Date: 2018/9/19 下午5:23
 */
@Data
@NoArgsConstructor
public class ListReq<T> implements Serializable {

    private static final long serialVersionUID = -5073128596098638114L;
    @NotEmpty
    @Valid
    List<T> data;
}
