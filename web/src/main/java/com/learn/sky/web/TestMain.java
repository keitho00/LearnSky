package com.learn.sky.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: JiuBuKong
 * @Date: 2020/9/4 10:41 AM
 */
public class TestMain {
    private static Map<Long, Map<String, String>> cacheMap = new ConcurrentHashMap<>();
    private static String s = "dneowdneowndoewdeownddmioewnhdoewndoewndoewndonewdonewodinweoidnewiodneoiwdnoiewndoewndoewoewndioendwendowendoewndkewlmdklasndioewndewde";

    public static void main(String[] args) {

        System.out.println("sleep ..");
        try {

            do {
                long now = System.currentTimeMillis();
                Map<String, String> map = cacheMap.get(now);
                if (map == null) {
                    map = new ConcurrentHashMap<>();
                }
                for (int i = 0; i < 1000; i++) {
                    map.put(i + "xxx", System.currentTimeMillis() + s);
                }
                cacheMap.put(now, map);
                Thread.sleep(10);
                if (now % 10 == 1) {
                    for (Long key : cacheMap.keySet()) {
                        cacheMap.remove(key);
                    }
                }
                System.out.println("add .." + cacheMap.size());
            } while (true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
