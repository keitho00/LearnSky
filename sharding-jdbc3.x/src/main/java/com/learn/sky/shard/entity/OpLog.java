package com.learn.sky.shard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * op_log
 * @author 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpLog implements Serializable {
    private static final long serialVersionUID = -6427341951557227610L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 操作类型(新增、修改、删除)
     */
    private Integer opType;

    /**
     * 关联数据类型(功能配置项、组件配置项、功能配置、组件配置、活动配置)
     */
    private Integer refType;

    /**
     * 关联数据ID
     */
    private Integer refId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建时间
     */
    private Long createTime;


}