package com.kcode.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wanghao
 * @Date: 2018/4/23 下午6:50
 */
public class CallbackDemo {
    public static void main(String[] args) {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1000, TimeUnit.MILLISECONDS)
                .build();
        for(int i=0;i<100;i++){
            try{
                String result=cache.get("java", () -> "hello java! "+System.currentTimeMillis());
                System.out.println(result);
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
