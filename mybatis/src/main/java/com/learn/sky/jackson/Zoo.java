package com.learn.sky.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @Date: 2018/5/14 下午8:52
 */

public class Zoo {


    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Cat cat = new Cat();
        cat.setLikesCream(true);
        cat.setLives(1);
        cat.setName("1221");
        String json = mapper.writeValueAsString(cat);
        Animal animal = mapper.readValue(json, Animal.class);
    }

}