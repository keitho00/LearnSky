package org.mybatis.generator.plugins;

import com.google.gson.Gson;

/**
 * @Date: 2018/4/25 下午5:51
 */
public class Test {
    public static void main(String[] args) {
        Cat cat = new Cat("cat");
        Dog dog = new Dog("dog");
        Cat cat1 = new Cat("cat1");
        Dog dog1 = new Dog("dog1");
        System.out.println(new Gson().toJson(cat));
        System.out.println(new Gson().toJson(dog));
        System.out.println(new Gson().toJson(cat1));
        System.out.println(new Gson().toJson(dog1));
    }
}
