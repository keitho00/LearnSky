package com.learn.sky.squirrel;

/**
 * @Date: 2018/5/4 下午5:14
 */
public class DemoContext {
    private Integer level;
    private String name;
    private Integer num;


    public DemoContext(Integer level, String name, Integer num) {
        this.level = level;
        this.name = name;
        this.num = num;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
