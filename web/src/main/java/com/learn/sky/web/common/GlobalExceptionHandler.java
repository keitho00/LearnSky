package com.learn.sky.web.common;

import com.learn.sky.web.entity.common.CommonResult;
import com.learn.sky.web.entity.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author: wanghao
 * @Date: 2018/6/21 下午4:51
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理一般异常。
     */
    @ExceptionHandler(Exception.class)
    public CommonResult handle(Exception e) {
        log.error("全局异常处理：", e);

        return new CommonResult<>(Constants.ERR_CODE, "系统错误", e.getMessage());
    }


    /**
     * 参数绑定异常返回处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleConstraintViolationException(MethodArgumentNotValidException e) {
        String msg = buildErrMsg(e.getBindingResult());
        log.info("全局异常处理参数异常: {}", e);

        return new CommonResult<>(Constants.ERR_CODE, msg, null);
    }

    /**
     * 参数绑定异常返回处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public CommonResult handleBindException(BindException e) {
        String msg = buildErrMsg(e.getBindingResult());
        log.info("全局异常处理参数异常: {}", e);

        return new CommonResult<>(Constants.ERR_CODE, msg, null);
    }

    @ExceptionHandler(NumberFormatException.class)
    public CommonResult handleNumberFormatException(NumberFormatException e) {
        log.info("全局异常处理参数异常: {}", e);
        return new CommonResult<>(Constants.ERR_CODE, "参数转换错误，请注意检查填写内容", e.getMessage());
    }

    @ExceptionHandler(ExecutionException.class)
    public CommonResult handleExecutionException(ExecutionException e) {
        log.info("缓存逻辑执行异常: {}", e);
        if (e.getCause() instanceof NumberFormatException) {
            return new CommonResult<>(Constants.ERR_CODE, "参数转换错误，请注意检查填写内容", e.getMessage());
        }
        return new CommonResult<>(Constants.ERR_CODE, "缓存错误", e.getMessage());
    }

    private String buildErrMsg(BindingResult br) {
        List<ObjectError> errList = br.getAllErrors();
        StringBuilder strBuilder = new StringBuilder();
        for (ObjectError error : errList) {
            strBuilder.append(error.getDefaultMessage());
        }

        return strBuilder.toString();
    }
}
