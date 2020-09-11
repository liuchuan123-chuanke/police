package com.imeng.common.utils;

import java.util.Random;

/**
 * @author wwj
 * 2020/1/7 21:31
 */
public class CodeUtil {

    /**
     * 随机生成字符 或 者数字
     *
     * @return
     */
    public static String getRandom(int gen) {
        String value = "";
        Random random = new Random();
        String charOrNum = gen % 2 == 0 ? "char" : "num";
        if ("char".equals(charOrNum)) {
            //字符
            int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
            int ascii = random.nextInt(26);
            value += (char) (ascii + temp);
        } else if ("num".equals(charOrNum)) {
            //是数字
            value += String.valueOf(random.nextInt(10));
        }
        return value;
    }

    /**
     * 按类型长度生成一定长度 随机字符串
     *
     * @param gen
     * @param length
     * @return
     */
    public static String getRandom(int gen, int length) {
        String ret = "";
        while (length > 0) {
            ret += getRandom(gen);
            length--;
        }
        return ret;
    }

    /**
     * 固定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        return getRandom(0, length);
    }

    /**
     * 固定长度的随机数字字符
     *
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        return getRandom(1, length);
    }

    /**
     * 随机生成带字母和数字的字符串   含大小写
     *
     * @param length
     * @return
     */
    public static String getRandomMix(int length) {
        Random random = new Random();

        String ret = "";
        while (length > 0) {
            if (random.nextInt(10) % 2 == 0) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                int ascii = random.nextInt(26);
                ret += (char) (ascii + temp);
            } else {
                //是数字
                ret += String.valueOf(random.nextInt(10));
            }
            length--;
        }
        return ret;
    }
}
