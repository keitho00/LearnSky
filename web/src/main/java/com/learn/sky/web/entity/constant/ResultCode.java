package com.learn.sky.web.entity.constant;

/**
 * @Author: JiuBuKong
 * @Date: 2018/9/19 下午5:34
 */
public enum ResultCode {

    SUCCESS(0, "success"),
    FAIL(1, "fail");


    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
