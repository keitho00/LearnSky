package com.learn.sky.jackson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @Author: wanghao
 * @Date: 2018/5/14 下午9:11
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "dog", value = Dog.class),
        @JsonSubTypes.Type(name = "cat", value = Cat.class)
})
public abstract class Animal { // All animals have names, for our demo purposes...
    public String name;

    protected Animal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}