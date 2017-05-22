package com.metaship.edu.utils.security;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by guohuanmeng on 2017/5/1.
 */
public class Base64Util {
    public static String encrypt(String data){
        return new String(Base64.encodeBase64(data.getBytes(), true));
    }


    public static String decrypt(String data){
        return new String(Base64.decodeBase64(data.getBytes()));
    }


    public static void main(String[] args) {
        String text = "sssssffddsafd";
        String encrypt = Base64Util.encrypt(text);
        System.out.println(encrypt);
        System.out.println(Base64Util.decrypt(encrypt));
    }
}
