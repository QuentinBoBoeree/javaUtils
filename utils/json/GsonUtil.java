package com.metaship.edu.utils.json;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by guohuanmeng on 2017/4/30.
 */
public class GsonUtil {
    /**
     * @MethodName : toJson
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     * @param src
     *            :将要被转化的对象
     * @return :转化后的JSON串
     */
    public static String toJson(Object src) {
        Gson gson = new Gson();
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }

    /**
     * 将json字符串转化为Bean对象
     * @param json
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> E toObj(String json, Class<E> clazz){
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * 将json字符串转化为Type对应的对象
     * @param json
     * @param type
     * @param <E>
     * @return
     */
    public static <E> E toObj(String json, Type type){
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * 将字符串转化为list集合.
     * @param jsonStr
     * @param typeToken
     * @param <E>
     * @return
     */
    public static <E> List<E> toList(String jsonStr, TypeToken<List<E>> typeToken) {
        Gson gson = new Gson();
        List<E> retList = gson.fromJson(jsonStr,  typeToken.getType());
        return retList;
    }

}
