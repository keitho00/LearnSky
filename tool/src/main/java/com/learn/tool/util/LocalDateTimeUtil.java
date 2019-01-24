package com.learn.tool.util;


import com.learn.tool.entity.DataFormatEnum;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author: JiuBuKong
 * @Date: 2019/1/10 下午4:34
 */
public class LocalDateTimeUtil {

    private static final long THOUSAND = 1000L;

    /**
     * 时间戳（s） 转换为指定格式的字符串
     *
     * @param time    秒
     * @param pattern
     * @return
     */
    public static String format(Integer time, DataFormatEnum pattern) {
        Instant instant = Instant.ofEpochMilli(time * THOUSAND);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getFormat());
        return localDateTime.format(df);
    }


    /**
     * 字符串按指定格式转 LocalDateTime
     *
     * @param string
     * @param pattern
     * @return
     */
    public static LocalDateTime parse(String string, DataFormatEnum pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getFormat());
        return LocalDateTime.parse(string, df);
    }

    /**
     * 时间戳转 LocalDateTime
     *
     * @param time 秒
     * @return
     */
    public static LocalDateTime parse(Integer time) {
        Instant instant = Instant.ofEpochMilli(time * THOUSAND);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

}
