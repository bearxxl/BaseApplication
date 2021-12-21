package com.utry.baselib.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.utry.baselib.app.AppInit;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Title: SPUtil.java
 * Description:
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/9/14 15:11
 * Created by xueli.
 */
public class SPUtil {


    private static SharedPreferences sp;


    private static SharedPreferences getSp() {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(AppInit.getContext());
        }
        return sp;
    }

    public static boolean hasKey(String key) {
        return getSp().contains(key);
    }

    public static String getString(String key, String defaultValue) {
        return getSp().getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getSp().edit().putString(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSp().getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSp().edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        getSp().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return getSp().getInt(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        getSp().edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return getSp().getFloat(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        getSp().edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return getSp().getLong(key, defaultValue);
    }

    public static void remove(String... keys) {
        for (String key : keys) {
            if (getSp().contains(key)) {
                getSp().edit().remove(key).apply();
            }
        }
    }

    /**
     * save json string of data list to share preference
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist)
            return;

        Gson gson = new Gson();
        //change datalist to json
        String strJson = gson.toJson(datalist);
        Log.d(TAG, "setDataList, json:" + strJson);
        getSp().edit().clear();
        getSp().edit().putString(tag, strJson);
        getSp().edit().commit();
    }

    /**
     * get data List from share preferences
     *
     * @param tag share preferences data tag
     * @param cls target list element object class
     * @return list
     */
    public static <T> List<T> getDataList(String tag, Class<T> cls) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getSp().getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Log.d(TAG, "getDataList, json:" + strJson);
        try {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for (JsonElement jsonElement : array) {
                datalist.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage());
        }
        return datalist;
    }

    /**
     * save json string of data to share preference
     *
     * @param tag
     * @param data object
     */
    public static <T> void setData(String tag, T data) {
        if (null == data)
            return;

        Gson gson = new Gson();
        //change data to json
        String strJson = gson.toJson(data);
        Log.d(TAG, "setData, json:" + strJson);
        getSp().edit().clear();
        getSp().edit().putString(tag, strJson);
        getSp().edit().commit();
    }

    /**
     * get data from share preferences
     *
     * @param tag share preferences data tag
     * @param cls target object class
     * @return target object or null if error happyed
     */
    public static  <T> T getData(String tag, Class<T> cls) {
        T data = null;
        String strJson = getSp().getString(tag, null);
        if (null == strJson) {
            return null;
        }
        Log.d(TAG, "getData, json:" + strJson);
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = new JsonParser().parse(strJson);
            data = gson.fromJson(jsonElement, cls);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage());
        }
        return data;
    }

}
