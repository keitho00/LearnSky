package com.learn.tool.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: JiuBuKong
 * @Date: 2019/5/31 下午6:03
 */
public class GenericsUtils {

    /**
     * 获取类的泛型参数
     *
     * @param clazz
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}