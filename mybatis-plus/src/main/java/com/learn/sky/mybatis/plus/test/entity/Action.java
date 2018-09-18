package com.learn.sky.mybatis.plus.test.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author WangHao
 * @since 2018-06-12
 */
public class Action extends Model<Action> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 功能模板ID
     */
    @TableField("action_template_id")
    private Integer actionTemplateId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionTemplateId() {
        return actionTemplateId;
    }

    public void setActionTemplateId(Integer actionTemplateId) {
        this.actionTemplateId = actionTemplateId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Action{" +
        ", id=" + id +
        ", actionTemplateId=" + actionTemplateId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
