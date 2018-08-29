package org.mybatis.generator.plugins;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: wanghao
 * @Date: 2018/4/25 下午5:50
 */
public class Dog extends Base {
    public Dog(String s) {
        this.string = s;
    }

        public static void main(String[] args) {
            List<String> list = Lists.newArrayList();
            list.add("one");
            list.add("three");
            list.add("two");
            list.add("two");
            list.add("three");
            System.out.println(Iterables.removeIf(list, new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return input.length() == 5;
                }
            }));
            // [one, two, two]
            System.out.println(list.toString());
        }
}
