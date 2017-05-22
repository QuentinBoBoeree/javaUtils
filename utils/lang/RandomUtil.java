package com.metaship.edu.utils.lang;

import java.util.Random;

/**
 * Created by guohuanmeng on 2017/5/2.
 */
public class RandomUtil {
    /**
     * 获取指定长度的随机数
     *
     * @param size
     * @return
     */
    public static String generateRandomCode(int size) {
        int max = 0;
        int min = (int) Math.pow(10, size - 1);
        for (int i = 0; i < size; i++) {
            max += Math.pow(10, i) * 9;
        }
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s + "";
    }

    /**
     * 生成start到end范围之内的随机数
     *
     * @param start
     * @param end
     * @return
     */
    public static int generateStartEndRandomCode(int start, int end) {
        int min = start;
        int max = end;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 计算是否中奖
     *
     * @param start
     * @param end
     * @param prob
     * @return
     */
    public static boolean lotteryCalculate(int start, int end, double prob) {
        int min = start;
        int max = end;
        Random random = new Random();
        int result = random.nextInt(max) % (max - min + 1) + min;
        //计算中奖区域
        int lotteryRange = (int) (end * prob);
        int rangeStart = end - lotteryRange;
        if (result >= rangeStart && result <= end) {
            return true;
        }
        return false;
    }

    /**
     * java生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String getCharAndNum(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static void main(String[] args) {
        System.out.println(getCharAndNum(8));
    }
}
