package com.metaship.edu.utils.security;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by guohuanmeng on 2017/5/1.
 * md5加密，生成摘要信息.
 */
public class MD5Util {

    /**
     * md5生成摘要.
     * @param data
     * @return
     */
    public static String encrypt(String data){
        return DigestUtils.md5Hex(data);
    }

    public static void main(String[] args) {
        String a = "sssdfafdafdad";
        System.out.println(MD5Util.encrypt(a));
    }
}
