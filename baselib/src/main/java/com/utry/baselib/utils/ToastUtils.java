package com.utry.baselib.utils;

import android.widget.Toast;

import androidx.annotation.StringRes;

import com.utry.baselib.app.AppInit;


/**
 * Title: ToastUtils.java
 * Description:土司工具
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/8/31 15:09
 * Created by xueli.
 */
public class ToastUtils {
    public static void show(String toast) {
        Toast.makeText(AppInit.getContext(), toast, Toast.LENGTH_SHORT).show();
    }
    public static void show(@StringRes int toast) {
        Toast.makeText(AppInit.getContext(), toast, Toast.LENGTH_SHORT).show();
    }
}
