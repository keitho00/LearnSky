package con.leanrn.tool;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @Author: JiuBuKong
 * @Date: 2020/5/9 3:36 PM
 */
public class CaffeineTest {

    public static void main(String[] args) {

        Cache<String, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        Integer res1 = cache.getIfPresent("1");

        Integer res2 =  cache.get("1", (a) -> 1);

        Integer res3 = cache.getIfPresent("1");
    }
}
