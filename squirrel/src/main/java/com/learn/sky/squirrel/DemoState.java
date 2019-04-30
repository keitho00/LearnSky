package com.learn.sky.squirrel;

/**
 * @Date: 2018/5/4 下午4:55
 */
public enum DemoState {

    BEGIN(1, "开始"),
    LEADER_AGREE(2, "上级同意"),
    FINANCE_AGREE(3, "财务同意"),
    FINISH(4, "HR同意"),
    CANCEL(5, "撤销"),
    BOSS_AGREE(6, "boss同意");

    private Integer value;
    private String des;

    DemoState(Integer value, String des) {
        this.value = value;
        this.des = des;
    }

    public Integer getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }
}
