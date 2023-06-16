package com.learn.tool.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: JiuBuKong
 * @Date: 2020/7/6 4:25 PM
 */

public class ChangeTableTest {
    private static String head = inputStream2String(new File("begin.txt"));
    private static String end = inputStream2String(new File("end.txt"));
    private static String line = System.getProperty("line.separator");

    public static void main(String[] args) throws IOException {
        File file = new File("sql-auto.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime date = LocalDateTime.of(2020, 4, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2021, 1, 1, 0, 0);
        do {
            ZonedDateTime zonedDateTime = ZonedDateTime.of(date, ZoneId.of("PRC"));
            writeText(file, head + Integer.parseInt(zonedDateTime.format(sdf)) + end);
            date = date.plusDays(1);
        } while (date.isBefore(endTime));
    }

    public static void writeText(File file, String text) {
        if (text == null) {
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(line.getBytes());
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String inputStream2String(File file) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            return null;
        }
    }


}
