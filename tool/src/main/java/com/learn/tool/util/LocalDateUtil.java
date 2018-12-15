package com.learn.tool.util;


import com.learn.tool.entity.DataFormatEnum;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author: JiuBuKong
 * @Date: 2018/11/15 上午10:20
 */
public class LocalDateUtil {

    private static final int DAY = 86399;

    private static final long THOUSAND = 1000L;

    public static LocalDate parse(String string, DataFormatEnum pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getFormat());
        return LocalDate.parse(string, df);
    }

    public static LocalDate parse(Integer time) {
        Instant instant = Instant.ofEpochMilli(time * THOUSAND);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
    }

    public static String format(Integer time, DataFormatEnum pattern) {
        Instant instant = Instant.ofEpochMilli(time * THOUSAND);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getFormat());
        return LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth()).format(df);
    }

    public static String format(LocalDate localDate, DataFormatEnum pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern.getFormat());
        return localDate.format(df);
    }

    /**
     * 获得一天的第一秒时间
     *
     * @param localDate
     * @return
     */
    public static int getBeginOfDay(LocalDate localDate) {
        return (int) (localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
    }

    /**
     * 获得一天的最后一秒时间
     *
     * @param localDate
     * @return
     */
    public static int getEndOfDay(LocalDate localDate) {
        return getBeginOfDay(localDate) + DAY;
    }

    /**
     * 获取上月时间
     * 格式：yyyyMM
     *
     * @return
     */
    public static String getLastMonth() {
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        return lastMonth.format(dateTimeFormatter);
    }

}
