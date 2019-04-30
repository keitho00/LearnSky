package com.learn.sky.squirrel;

/**
 * @Date: 2018/5/4 下午4:53
 */
public enum DemoEvent {
    LEADER_AGREE(2, "上级同意"),
    FINANCE_AGREE(3, "财务同意"),
    HR_AGREE(4,"全部同意"),
    BOSS_AGREE(6, "boss同意"),
    MIN_CANCEL(7,"自己取消"),
    LEADER_NOT_AGREE(8,"上级不同意") ,
    FINANCE_NOT_AGREE(9,"财务不同意"),
    BOSS_NOT_AGREE(10,"boos不同意"),
    HR_NOT_AGREE(11,"hr不同意");
    private Integer value;
    private String des;

    DemoEvent(Integer value, String des) {
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
