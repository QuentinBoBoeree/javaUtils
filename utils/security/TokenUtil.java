package com.metaship.edu.utils.security;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * Created by guohuanmeng on 2017/4/28.
 */
public class TokenUtil {
    private static final String SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int KEY_LEN = 32; // 私钥长度
    private static final String PRIVATE_KEY = "x9YdfNvMduJa0rqRr9pTxprL4wwG0i0N"; // 私钥
    private static final Charset CHARSET = Charsets.UTF_8;
    private static final int EXPIRE_TIME = 1 * 24 * 60 * 60 * 1000; // 1天

    static {
        int len = SET.length();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < KEY_LEN; i++)
            key.append(SET.charAt((int) (Math.random() * len)));
    }


    public static String createToken(String userName) {
        return Hashing.md5().newHasher().putLong(System.currentTimeMillis())
                .putString(userName, CHARSET)
                .putString(PRIVATE_KEY, CHARSET).hash().toString();
    }

    private static String findKey(long unique, String prefix) {
        StringBuilder tokenKey = new StringBuilder();
        tokenKey.append(prefix);
        tokenKey.append(unique);
        return tokenKey.toString();
    }



    public static void main(String[] args) {
        String mobile = "13399877765";
        String token = createToken(mobile);
        System.out.println(token);
    }
}
