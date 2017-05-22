package com.metaship.edu.utils.lang;

import java.util.UUID;

/**
 * @author QuentinBo
 * @mail boboeree@qq.com
 * @create 2017/3/30.
 * @time 10:52
 */
public class UUIDUtil {
    /**
     * 生成uuid
     * @return
     */
    public static String createUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成无分隔符-的uuid
     * @return
     */
    public static String createNoSepUUID() {
        String str = createUUID();
        // 去掉"-"符号
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }

    /**
     * 生成uuid
     * @return
     */
    public static String getUUID() {
        String str = createUUID();
        // 去掉"-"符号
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return str + "," + temp;
    }


}
