package com.learn.sky.web.lock;

import java.lang.annotation.*;

/**
 * 分布式锁标记
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DLock {

    //锁的唯一id  默认组成service:function  设定后覆盖默认
    String prefix() default "";

    //参数加入生成prefix  {0} 代表第一个参数  默认参数不加入生成prefix
    int[] argsIndex() default {};

    long timeOut() default 10;

    int expireTime() default 20;


}
