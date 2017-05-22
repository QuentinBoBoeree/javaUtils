package com.metaship.edu.utils.security;

import com.google.gson.JsonObject;
import com.metaship.edu.utils.http.HttpUtil;
import com.metaship.edu.utils.http.NetUtil;
import com.metaship.edu.utils.json.GsonUtil;
import com.metaship.edu.utils.lang.RandomUtil;
import com.sun.crypto.provider.HmacSHA1;
import com.sun.jmx.snmp.Timestamp;
import org.apache.commons.codec.digest.HmacUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by guohuanmeng on 2017/5/9.
 */
public class Test {
    public static void print1(){
        Timestamp a = new Timestamp();
        long timestamp = a.getDateTime();
        String nonce = RandomUtil.generateRandomCode(6);
        String t = Long.toString(timestamp).substring(0, 10);
        String region="bj";
        System.out.println(timestamp + ",," + nonce);


        String secreKey = "e2A3g3C9pFQM2SfcLp31jFVUFjxvdCWh";
        String domain = "wenzhi.api.qcloud.com/v2/index.php";
        String queryString = "Action=LexicalAnalysis&Nonce=" +nonce
                + "&Region="+region+"&SecretId=AKIDHMCm6EAi4D7nEQn4WicHfngMlutdMNN8&Timestamp=" + t +
                "&code=2097152&text=";
        String query = "你好";

        String method = "GET";
        String str = method + domain + "?" + queryString+query;

        String sig = Base64.encode(HmacUtils.hmacSha1(secreKey, str));
        System.out.println(sig);
        String last = "https://" + domain + "?" +queryString + query
                + "&Signature=" + URLEncoder.encode(sig);
        System.out.println(last);
    }

    public static void print5(){
        Timestamp a = new Timestamp();
        long timestamp = a.getDateTime();
        String nonce = RandomUtil.generateRandomCode(6);
        String t = Long.toString(timestamp).substring(0, 10);
        String region="bj";
        System.out.println(timestamp + ",," + nonce);


        String secreKey = "e2A3g3C9pFQM2SfcLp31jFVUFjxvdCWh";
        String domain = "wenzhi.api.qcloud.com/v2/index.php";
        String queryString = "Action=LexicalAnalysis&Nonce=" +nonce
                + "&Region="+region+"&SecretId=AKIDHMCm6EAi4D7nEQn4WicHfngMlutdMNN8&Timestamp=" + t +
                "&code=2097152&text=";
        String query = "你好";

        String method = "GET";
        String str = method + domain + "?" + queryString+query;

        String sig = Base64.encode(HmacUtils.hmacSha1(secreKey, str));
        System.out.println(sig);
        String last = "https://" + domain + "?" +queryString + query
                + "&Signature=" + URLEncoder.encode(sig);
        String s = HttpUtil.sendRequestByGet(last);


        System.out.println(s);
    }

    public static void print2(){
        Timestamp a = new Timestamp();
        long timestamp = a.getDateTime();
        String nonce = RandomUtil.generateRandomCode(6);
        String t = Long.toString(timestamp).substring(0, 10);
        String region="bj";
        System.out.println(timestamp + ",," + nonce);

        String secreKey = "e2A3g3C9pFQM2SfcLp31jFVUFjxvdCWh";
        String domain = "cvm.api.qcloud.com/v2/index.php";
        String queryString = "Action=DescribeInstances&Nonce=" +nonce
                + "&Region="+region+"&SecretId=AKIDHMCm6EAi4D7nEQn4WicHfngMlutdMNN8&Timestamp=" + t +
                "&instanceIds.0=ins-09dx96dg&limit=20&offset=0";

        String method = "GET";
        String str = method + domain + "?" + queryString;

        String sig = Base64.encode(HmacUtils.hmacSha1(secreKey, str));
        System.out.println(sig);
        String last = "https://"+domain + "?" + queryString
                + "&Signature=" + URLEncoder.encode(sig);
        System.out.println(last);
    }

    public static void print3(){
        Timestamp a = new Timestamp();
        long timestamp = a.getDateTime();
        String nonce = RandomUtil.generateRandomCode(6);
        String t = Long.toString(timestamp).substring(0, 10);
        String region="bj";
        System.out.println(timestamp + ",," + nonce);

        String secreKey = "e2A3g3C9pFQM2SfcLp31jFVUFjxvdCWh";
        String domain = "wenzhi.api.qcloud.com/v2/index.php";
        String queryString = "Action=TextClassify&Nonce=" +nonce
                + "&Region="+region+"&SecretId=AKIDHMCm6EAi4D7nEQn4WicHfngMlutdMNN8&Timestamp=" + t +
                "&content=腾讯入股京东";

        String method = "GET";
        String str = method + domain + "?" + queryString;

        String sig = Base64.encode(HmacUtils.hmacSha1(secreKey, str));
        System.out.println(sig);
        String last = "https://"+domain + "?" + queryString
                + "&Signature=" + URLEncoder.encode(sig);
        System.out.println(last);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //print5();
        System.out.println(URLEncoder.encode("皇帝"));
        toStr("\u9632\u96e8\u7684\u4f1e\u3002\u4e00\u822c\u7528\u6cb9\u7eb8\u3001\u6cb9\u5e03\u6216\u5851\u6599\u5e03\u7b49\u505a\u6210\u3002 \u5b8b  \u9ad8\u627f");
    }

    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }

    public static void toStr(String unicodeStr) throws UnsupportedEncodingException {
                //System.out.println(new String(unicodeStr.getBytes("ISO8859-1"), "GBK"));
        System.out.println(unicodeStr);
    }

}
