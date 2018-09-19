package com.learn.sky.web.entity.common;

import com.learn.sky.web.entity.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用响应
 */
@Data
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -5888997213964143847L;
    private int error;
    private String msg;
    private T data;
    private String redirectUrl;

    public CommonResult() {
    }

    public CommonResult(int error, String msg, T data) {
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(int error, String redirectUrl) {
        this.error = error;
        this.redirectUrl = redirectUrl;
    }

    public CommonResult(ResultCode code, T data, String redirectUrl) {
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS, data, null);
    }

    public static <T> CommonResult<T> fail(int code, String msg, T data) {
        return new CommonResult<>(code, msg, data);
    }

    public static <T> CommonResult<T> fail(ResultCode code, T data) {
        return new CommonResult<>(code, data, null);
    }

}
