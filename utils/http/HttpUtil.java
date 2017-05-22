package com.metaship.edu.utils.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guohuanmeng on 2016/5/12.
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 从request body中获取json字符串
     *
     * @param request
     * @return
     */
    public static String getParamFromBody(HttpServletRequest request) {
        String params = null;
        /*StringBuffer sb = new StringBuffer();
        InputStream is = null;
        InputStreamReader isr = null;*/
        try {
            /*is = request.getInputStream();
            isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            params = sb.toString();*/
            request.setCharacterEncoding("UTF-8");
            int size = request.getContentLength();
            InputStream is = request.getInputStream();

            byte[] reqBodyBytes = readBytes(is, size);

            params = new String(reqBodyBytes);
        } catch (IOException e) {
            logger.error("从request中获取流出错", e);
        }
        return params;
    }

    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen
                            - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                logger.error("读取出错", e);
            }
        }

        return new byte[]{};
    }

    /**
     * @param url
     * @return
     */
    public static String sendRequestByGet(String url) {
        String result = "";
        BufferedReader in = null;
        HttpURLConnection connection = null;
        try {
            logger.info("get请求参数url:" + url);
            URL paostUrl = new URL(url);
            //请求配置
            connection = (HttpURLConnection) paostUrl.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = in.readLine();
            while (line != null) {
                result += line;
                line = in.readLine();
            }
        } catch (Exception e) {
            logger.error("get请求发生异常" + ",url=" + url, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }
            } catch (IOException e) {
                logger.error("error", e);
            }
        }
        return result;
    }

    public static String sendMessage(String sURL, String content) throws Exception {
        String result = ""; //返回结果
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(sURL);
        RequestEntity requestEntity = new StringRequestEntity(content, "application/json", "utf-8");

        postMethod.setRequestEntity(requestEntity);
        client.executeMethod(postMethod);


        result = postMethod.getResponseBodyAsString();
        System.out.print(result);
        return result;
    }


    /**
     * post请求
     *
     * @param url
     * @param paramStr
     * @return
     */
    public static String sendRequestByPost(String url, String paramStr) {
        String result = "";
        BufferedReader in = null;
        HttpURLConnection connection = null;
        OutputStream out = null;
        try {
            logger.info("post请求参数url:" + url + ",paramStr:" + paramStr);
            URL paostUrl = new URL(url);
            //参数配置
            connection = (HttpURLConnection) paostUrl.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true); //http正文内，因此需要设为true, 默认情况下是false
            connection.setDoInput(true); //设置是否从httpUrlConnection读入，默认情况下是true
            connection.setUseCaches(false); //Post 请求不能使用缓存
            connection.setInstanceFollowRedirects(true); //URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            connection.setConnectTimeout(30000); //设置连接主机超时时间
            connection.setReadTimeout(45000); //设置从主机读取数据超时

            //打开连接
            out = connection.getOutputStream();
            out.write(paramStr.getBytes());
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = in.readLine();
            while (line != null) {
                result += line;
                line = in.readLine();
            }
        } catch (Exception e) {
            logger.error("post请求发生异常" + ",url=" + url + ",paramStr:" + paramStr, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                logger.error("error", e);
            }
        }
        return result;
    }

}
