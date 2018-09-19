package com.learn.sky.web.entity.common;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class ListSumRes<T> implements Serializable {

    private static final long serialVersionUID = 7806557383445715902L;

    private List<T> list;
    private Double totalSum;
    //分页信息
    private PageInfo page;

    public ListSumRes() {
        list = Lists.newArrayList();
        totalSum = 0.0;
        page = new PageInfo();
    }


}
