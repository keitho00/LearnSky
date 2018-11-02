package com.learn.sky.web.aspect;

import java.lang.annotation.*;

/**
 * @Author: JiuBuKong
 * @Date: 2018/10/19 下午4:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
}
