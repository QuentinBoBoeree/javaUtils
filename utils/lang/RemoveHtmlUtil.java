package com.metaship.edu.utils.lang;

/**
 * @author QuentinBo
 * @mail boboeree@qq.com
 * @create 2017/5/16.
 * @time 16:19
 */

/**
 * 去掉HTML标签
 */
public class RemoveHtmlUtil {
    //去掉HTML的段落标签和空格
    public static String removeParagraphTag(String content) {
        content = content.replaceAll("(<p[^>]*>)|(&nbsp;)|(\\s)", "");
        return content.replace("</p>", "");
    }

    public static void main(String[] args) {
        String content = "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;哥汉口两个地方呵呵哈哈哈很积极几把哈哈叽叽叽叽还合计叽叽叽叽哈哈叽叽叽叽</p><p dir=\\\"ltr\\\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  预告函紧急集合白白净净快叫爸爸不急叽叽叽叽不回家好吧v回家结结巴巴哈哈急急急</p>";
        System.out.println(content.length());
        content = RemoveHtmlUtil.removeParagraphTag(content);
        System.out.println(content);
    }
}
