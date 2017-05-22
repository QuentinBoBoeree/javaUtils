package com.metaship.edu.utils.security;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by guohuanmeng on 2017/5/1.
 */
public class SHA1Util {
    public static String encrypt(String data){
        return DigestUtils.sha1Hex(data);
    }

    public static void main(String[] args) {
        String a ="sss";
        System.out.println(encrypt(a));
    }
}
