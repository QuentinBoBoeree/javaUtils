package com.metaship.edu.utils.lang;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by guohuanmeng on 2017/5/14.
 */
public class StringUtil {
    /**
     * 首字母大写
     * @param clazzName
     * @return
     */
    public static String upperFirstChar(String clazzName){
        String firstChar = clazzName.substring(0, 1);
        return firstChar.toUpperCase() + clazzName.substring(1);
    }

    /**
     * 将大写字母转化为下划线加小写字母
     * @param name
     * @return
     */
    public static String parseUpperToLine(String name) {
        char[] charArray = name.toCharArray();
        List<Character> list = Lists.newArrayList();
        for(int i=0; i<charArray.length; i++){
            if(Character.isUpperCase(charArray[i])){
                list.add('_');
                list.add(Character.toLowerCase(name.charAt(i)));
            }else{
                list.add(charArray[i]);
            }
        }
        charArray = new char[list.size()];
        for(int i=0; i<list.size(); i++){
            charArray[i] = list.get(i);
        }
        return new String(charArray);
    }

}
