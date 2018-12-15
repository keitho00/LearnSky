package com.learn.tool.entity;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/15 上午10:47
 */
public enum DataFormatEnum {

    PATTERN_GRACE("yyyy/MM/dd HH:mm:ss"),
    PATTERN_GRACE_NORMAL("yyyy/MM/dd HH:mm"),
    PATTERN_GRACE_SIMPLE("yyyy/MM/dd"),
    PATTERN_CLASSICAL("yyyy-MM-dd HH:mm:ss"),
    PATTERN_CLASSICAL_NORMAL("yyyy-MM-dd HH:mm"),
    PATTERN_CLASSICAL_SIMPLE("yyyy-MM-dd");


    private String format;


    DataFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
