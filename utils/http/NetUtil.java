/**
 *
 */
package com.metaship.edu.utils.http;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author guohuanmeng
 */
public class NetUtil {

    /**
     * 获取请求调用方的ip地址。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本地ip
     * @return
     */
    public static String getLocalIp() {
        String ip = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();
        } catch (UnknownHostException e) {
            System.out.print("获取本地ip失败" + e);
        }
        if(StringUtils.isNotBlank(ip) && !"127.0.0.1".equals(ip)){
            return ip;
        }
        return null;
    }




}
