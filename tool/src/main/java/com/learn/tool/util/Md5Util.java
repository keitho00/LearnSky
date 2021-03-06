package com.learn.tool.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Date: 2018/7/16 上午11:31
 */
@Slf4j
public class Md5Util {

    public static String md5(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("md5 error ", e);
        }
        return "";

    }

    public static void main(String[] args) {
        String a = Md5Util.md5("biz_j$1548663588380$a60f-485e88ad387d-fb68-4105-510a3d20");
        System.out.println(a);
    }
}
