package org.mybatis.generator.plugins;

/**
 * @Author: wanghao
 * @Date: 2018/4/25 下午5:49
 */
public abstract class Base {
    String string;


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
