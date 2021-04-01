package com.example.android_demo.repository.net;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.android_demo.utils.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: var_rain.
 * Created date: 2020/7/23.
 * Description: 高性能数据缓存
 */
@SuppressWarnings("all")
public class MCache {

    // 数据缓存器
    private static Map<Object, Object> dataMap;
    // 储存对象
    private static SharedPreferences prefer;

    /**
     * 初始化
     *
     * @param context {@link Application}
     */
    public static void init(Application context) {
        MCache.dataMap = new HashMap<>();
        MCache.prefer = context.getSharedPreferences("app_cache", Context.MODE_PRIVATE);
    }

    /**
     * 对象数据缓存
     *
     * @param key  键
     * @param data 数据对象
     */
    public static void put(String key, Object data) {
        MCache.put(key, data, true);
    }

    /**
     * 对象数据缓存
     *
     * @param key       键
     * @param data      数据对象
     * @param diskCache 是否缓存到磁盘
     */
    public static void put(String key, Object data, boolean diskCache) {
        // 内存缓存
        MCache.dataMap.put(key, data);
        if (diskCache && data != null) {
            // 磁盘缓存
            if (data instanceof Integer) {
                // int
                MCache.prefer.edit().putInt(key, (Integer) data).apply();
            } else if (data instanceof Long) {
                // long
                MCache.prefer.edit().putLong(key, (Long) data).apply();
            } else if (data instanceof Float) {
                // float
                MCache.prefer.edit().putFloat(key, (Float) data).apply();
            } else if (data instanceof Boolean) {
                // boolean
                MCache.prefer.edit().putBoolean(key, (Boolean) data).apply();
            } else if (data instanceof String) {
                // String
                MCache.prefer.edit().putString(key, (String) data).apply();
            } else {
                // Object
                MCache.prefer.edit().putString(key, JSON.toJson(data)).apply();
            }
        }
    }

    /**
     * 获取对象数据缓存
     *
     * @param key 键
     * @param cls 数据类型
     * @param <T> 泛型
     * @return 返回指定类型的数据对象或null
     */
    public static <T> T object(String key, Class<T> cls) {
        if (MCache.dataMap.containsKey(key)) {
            return (T) MCache.dataMap.get(key);
        }
        String result = MCache.prefer.getString(key, null);
        if (result != null) {
            T data = JSON.toObject(result, cls);
            MCache.dataMap.put(key, data);
            return data;
        }
        return null;
    }

    /**
     * 获取String类型的数据
     *
     * @param key 键
     * @param def 默认
     * @return 返回获取到的数据或默认值
     */
    public static String get(String key, String def) {
        if (MCache.dataMap.containsKey(key)) {
            return (String) MCache.dataMap.get(key);
        }
        String result = MCache.prefer.getString(key, def);
        MCache.dataMap.put(key, result);
        return result;
    }

    /**
     * 获取Boolean类型的数据
     *
     * @param key 键
     * @param def 默认
     * @return 返回获取到的数据或默认值
     */
    public static boolean get(String key, boolean def) {
        if (MCache.dataMap.containsKey(key)) {
            return (boolean) MCache.dataMap.get(key);
        }
        boolean result = MCache.prefer.getBoolean(key, def);
        MCache.dataMap.put(key, result);
        return result;
    }

    /**
     * 获取Float类型的数据
     *
     * @param key 键
     * @param def 默认
     * @return 返回获取到的数据或默认值
     */
    public static float get(String key, float def) {
        if (MCache.dataMap.containsKey(key)) {
            return (float) MCache.dataMap.get(key);
        }
        float result = MCache.prefer.getFloat(key, def);
        MCache.dataMap.put(key, result);
        return result;
    }

    /**
     * 获取Int类型的数据
     *
     * @param key 键
     * @param def 默认
     * @return 返回获取到的数据或默认值
     */
    public static int get(String key, int def) {
        if (MCache.dataMap.containsKey(key)) {
            return (int) MCache.dataMap.get(key);
        }
        int result = MCache.prefer.getInt(key, def);
        MCache.dataMap.put(key, result);
        return result;
    }

    /**
     * 获取Long类型的数据
     *
     * @param key 键
     * @param def 默认
     * @return 返回获取到的数据或默认值
     */
    public static long get(String key, long def) {
        if (MCache.dataMap.containsKey(key)) {
            return (long) MCache.dataMap.get(key);
        }
        long result = MCache.prefer.getLong(key, def);
        MCache.dataMap.put(key, result);
        return result;
    }

    /**
     * 清理内存缓存
     *
     * @param key 键
     */
    public static void remove(String key) {
        if (MCache.dataMap.containsKey(key)) {
            MCache.dataMap.remove(key);
        }
    }

    /**
     * 清理内存缓存并删除磁盘缓存
     *
     * @param key 键
     */
    public static void delete(String key) {
        MCache.remove(key);
        MCache.prefer.edit().remove(key).apply();
    }

    /**
     * 清空内存中的数据
     */
    public static void clean() {
        MCache.dataMap.clear();
    }
}
